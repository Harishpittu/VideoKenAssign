package com.technologies.pittu.videokenassignment.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;

import com.technologies.pittu.videokenassignment.R;
import com.technologies.pittu.videokenassignment.views.adapter.ContentAdapter;
import com.technologies.pittu.videokenassignment.databinding.ActivityPlayerBinding;
import com.technologies.pittu.videokenassignment.interfaces.OnItemClickListener;
import com.technologies.pittu.videokenassignment.model.Content;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayerActivity extends AppCompatActivity {
    
    private static final String TAG = "MVPWebViewPlayer : ";
    public static final int MAX = 1000;
    private float CURRENT_DURATION = 0;
    private float totalVideoDuration;
    private boolean isInPlayCondition = false;
    private boolean isStartedPlaying = false;
    private boolean isControlsVisible = true;
    private boolean isSeekBarUnderFocus = Boolean.FALSE;
    private PlayerWebViewFuncitons playerWebViewFuncitons;
    private ProgressDialog progressDialog;
    public String videoId ;
    private ActivityPlayerBinding activityPlayerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityPlayerBinding = DataBindingUtil.setContentView(this, R.layout.activity_player);
        init();
        Bundle bundle = getIntent().getExtras();
        videoId = bundle.getString("videoId");
        showLoadingVideoDialog();

        playerWebViewFuncitons = new PlayerWebViewFuncitons(this, activityPlayerBinding.webView, videoId,
                new JavaScriptInterface(this), new ChromeClient());
    }

    /**
     * show loading dialog
     */
    private void showLoadingVideoDialog() {
        progressDialog = new ProgressDialog(PlayerActivity.this);
        progressDialog.setMessage("Loading Video...");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                quit();
            }
        });
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    /**
     * initialize data
     */
    private void init() {
        showControls();
        listeners();
        ArrayList<Content> contentArrayList = new ArrayList<>();
        contentArrayList.add(new Content(1, "Part Basics", 10000));
        contentArrayList.add(new Content(2, "Server Basics", 15000));
        contentArrayList.add(new Content(3, "Visualized Server", 22000));
        contentArrayList.add(new Content(4, "Backgrounder", 35000));
        contentArrayList.add(new Content(5, "Wm mum", 40000));
        contentArrayList.add(new Content(6, "Revision", 55000));
        contentArrayList.add(new Content(7, "Final Review", 75000));
        setupRecyclerView(activityPlayerBinding.recyclerView, contentArrayList);
    }

    /**
     * @param recyclerView recycler view
     */
    private void setupRecyclerView(RecyclerView recyclerView, List<Content> contentList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ContentAdapter(contentList, new OnItemClickListener() {
            @Override
            public void onClick(Object object) {
                Content content = (Content) object;
                playerWebViewFuncitons.seekVideoTo(content.getDuration() / 1000);
                activityPlayerBinding.contentLayout.setVisibility(View.GONE);
                hideControls();
                play();
            }
        }));
    }

    /**
     * listeners
     */
    private void listeners() {
        activityPlayerBinding.seekBar.setMax(MAX);
        activityPlayerBinding.seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                Log.d("discretebar", "onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
                isSeekBarUnderFocus = Boolean.TRUE;
                Log.d("onStartTrackingTouch", "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                isSeekBarUnderFocus = Boolean.FALSE;
                Log.d("onStartTrackingTouch", "onStopTrackingTouch");
                int progress = activityPlayerBinding.seekBar.getProgress();
                double secs = (progress * totalVideoDuration) / 1000;
                secs = Math.ceil(secs);
                Log.d(TAG, "onStopTrackingTouch :: progress = " + progress + "-- secs = " + secs);
                playerWebViewFuncitons.seekVideoTo(secs);
                play();
            }
        });

    }

    /**
     * on click of top layout
     * @param v view
     */
    public void onClickTopView(View v) {
        if (isControlsVisible) {
            isControlsVisible = false;
            hideControls();
        } else {
            isControlsVisible = true;
            showControls();
        }
    }

    /**
     * on click of content button
     * @param v view
     */
    public void onClickContent(View v) {
        activityPlayerBinding.contentLayout.setVisibility(View.VISIBLE);
        if (isInPlayCondition) {
            pause();
        }
    }

    /**
     * on click of close content
     *
     * @param v view
     */
    public void onClickCloseContent(View v) {
        activityPlayerBinding.contentLayout.setVisibility(View.GONE);
    }

    /**
     * on click of play
     *
     * @param v view
     */
    public void onClickPlay(View v) {
        if (isInPlayCondition) {
            pause();
        } else {
            play();
        }
    }

    /**
     * play conditions
     */
    private void play() {
        if (!isInPlayCondition) {
            playerWebViewFuncitons.play();
            activityPlayerBinding.playPause.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
            isInPlayCondition = true;
            hideControls();
        }
    }

    /**
     * pause conditions
     */
    private void pause() {
        if (isInPlayCondition) {
            playerWebViewFuncitons.pause();
            activityPlayerBinding.playPause.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
            activityPlayerBinding.fslayout.setVisibility(View.VISIBLE);
            isInPlayCondition = false;
        }
    }

    /**
     * show all controls
     */
    private void showControls() {
        activityPlayerBinding.playPause.setVisibility(View.VISIBLE);
        activityPlayerBinding.seekBar.setVisibility(View.VISIBLE);
        activityPlayerBinding.duration.setVisibility(View.VISIBLE);
        activityPlayerBinding.fslayout.setVisibility(View.VISIBLE);
        isControlsVisible = true;
    }

    /**
     * chrome client for loging messages from webview
     */
    private class ChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.d(TAG, "consoleMessage : " + consoleMessage.message());
            if (consoleMessage.message().matches("ready")) {
                play();
                hideControls();
            }
            return super.onConsoleMessage(consoleMessage);
        }
    }

    /**
     * javascript interface
     */
    private class JavaScriptInterface {
        JavaScriptInterface(Context context) {

        }

        @JavascriptInterface
        public void setVideoDuration(String duration) {
            Log.d(TAG, "#duration live vs current = " + CURRENT_DURATION);
            CURRENT_DURATION = Float.parseFloat(duration);
            try {
                changeSlider(Float.parseFloat(duration));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void getPlayerStatus(String state) {
            Log.d(TAG, "playerstatus : " + state);
        }

        @JavascriptInterface
        public void OnPlayerStateChange(String state) {
            Log.d(TAG, "state == " + state);
            if (Integer.parseInt(state) == 0) {
                playerWebViewFuncitons.stop();
                activityPlayerBinding.playPause.performClick();
            }
        }

        @JavascriptInterface
        public void setTotalVideoDuration(String duration) {
            Log.d(TAG, "setTotalVideoDuration = " + duration);
                    totalVideoDuration = Float.parseFloat(duration);
        }
    }

    /**
     * change slider on progress update
     * @param time time
     */
    private void changeSlider(final float time) {
        float progress = (time / totalVideoDuration) * 1000;
        final double d = Math.ceil(progress);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (!isSeekBarUnderFocus) {
                    activityPlayerBinding.seekBar.setProgress((int) d);
                }
                setDuration(time);
            }
        });
    }

    /**
     * update duration
     * @param sec sec
     */
    private void setDuration(float sec) {
        if (sec > 0) {
            if (!isStartedPlaying) {
                progressDialog.hide();
                pause();
                showControls();
                isStartedPlaying = true;
            }
        }
        int millisec = Math.round(sec * 1000);
        activityPlayerBinding.duration.setText("" + FormatedTime(millisec));
    }

    /**
     * get formatted time from millisec
     * @param millisec int
     * @return string
     */
    public String FormatedTime(int millisec) {
        int totalSecs = millisec / 1000;
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        return String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * hide allcontrols
     */
    private void hideControls() {
        if (!isSeekBarUnderFocus) {
            activityPlayerBinding.duration.setVisibility(View.GONE);
            activityPlayerBinding.fslayout.setVisibility(View.GONE);
            activityPlayerBinding.seekBar.setVisibility(View.GONE);
            activityPlayerBinding.playPause.setVisibility(View.GONE);
            isControlsVisible = false;
        }
    }

    @Override
    protected void onPause() {
        pause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        quit();
    }

    @Override
    public void onDestroy() {
        playerWebViewFuncitons.destroyWebview();
        super.onDestroy();
    }

    private void quit() {
        playerWebViewFuncitons.stop();
        finish();
    }
}