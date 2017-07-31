package com.technologies.pittu.videokenassignment.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

/**
 * Created by harish on 23/06/16.
 */

class PlayerWebViewFuncitons {

    private WebView webView;
    private String TAG = "PlayerWebViewFuncitons";

    /**
     * constructor
     *
     * @param activity        activity
     * @param relativeLayout  layout
     * @param videoId         videoid
     * @param object          object
     * @param webChromeClient chrome client
     */
    PlayerWebViewFuncitons(Activity activity, RelativeLayout relativeLayout, String videoId, Object object, WebChromeClient webChromeClient) {
        webView = new WebView(activity);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        webView.setLayoutParams(params);
        relativeLayout.addView(webView);
        loadWebviewWithVideo(videoId, object, webChromeClient);
    }

    /**
     * initialize webview
     *
     * @param VideoId         string
     * @param object          object
     * @param webChromeClient chrome client
     */
    @SuppressLint("JavascriptInterface")
    private void loadWebviewWithVideo(String VideoId, Object object, WebChromeClient webChromeClient) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(new WebviewClient());
        // extra
        webView.addJavascriptInterface(object, "JSHandler");
        webView.loadUrl("file:///android_asset/ytplayerOrig.html?" + VideoId);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setFocusable(false);
        webView.setFocusableInTouchMode(false);
        webView.setClickable(false);
    }

    /**
     *webview client
     */
    private class WebviewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.d(TAG, "onReceivedError : description = " + description);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading : url = " + url);
            return true;
        }
    }

    void seekVideoTo(double secs) {
        webView.loadUrl("javascript:m_seekVideo('" + secs + "')");
    }

    /**
     * play video
     */
    void play() {
        webView.loadUrl("javascript:m_playVideo()");
    }

    /**
     * pause video
     */
    void pause() {
        webView.loadUrl("javascript:m_pauseVideo()");
    }

    /**
     * stop video
     */
    void stop() {
        webView.loadUrl("javascript:m_stopVideo()");
    }

    /**
     * destroy webview
     */
    void destroyWebview() {
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
        webView = null;
    }

}
