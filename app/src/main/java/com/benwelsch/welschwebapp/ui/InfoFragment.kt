package com.benwelsch.welschwebapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.HttpAuthHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.benwelsch.welschwebapp.R

class InfoFragment : Fragment() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_statistics, container, false)
        val webView: WebView = root.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.setSupportZoom(true)
        webView.loadUrl("https://www.benwelsch.com")
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(viewx: WebView, urlx: String): Boolean {
                viewx.loadUrl(urlx)
                return false
            }

            override fun onReceivedHttpAuthRequest(view: WebView, handler: HttpAuthHandler, host: String, realm: String) {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext() /* Activity context */)
                val user = sharedPreferences.getString("user", "")
                val password = sharedPreferences.getString("password", "")
                if (sharedPreferences.getBoolean("private", true) && user != "" && password != "") {
                    handler.proceed(user, password)
                } else {
                    super.onReceivedHttpAuthRequest(view, handler, host, realm)
                }
            }
        }
        return root
    }
}