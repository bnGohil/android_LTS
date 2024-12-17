package com.example.lts.ui.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.webkit.ConsoleMessage
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.custom_component.CustomTopBar
import com.sqt.lts.navigation.route.WebViewRoute
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kPrimaryColor
import com.example.lts.utils.scaleSize

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(webViewRoute: WebViewRoute?=null,navHostController: NavHostController) {

    var isPageLoading by remember {
        mutableStateOf<Boolean>(false)
    }



    Scaffold(
        topBar = {
            CustomTopBar(navHostController = navHostController, title = webViewRoute?.title?:"")
        }
    ) {
        paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(kBackGround)
                .fillMaxSize()
                .padding(horizontal = 15.dp.scaleSize()),
        ) {



            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        // Enable JavaScript (if needed)
                        settings.javaScriptEnabled = true
//                        settings.useWideViewPort = true
//                        settings.databaseEnabled = true
                        settings.domStorageEnabled = true


                        // Set the custom WebViewClient
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                isPageLoading = true
                                println("onPageStarted")
                            }



                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isPageLoading = false
                                println("onPageFinished")
                                println("url : $url")
                                println("onPageFinished")
                                // Hide any loading indicator if you have one
                            }


                            @Deprecated("Deprecated in Java")
                            override fun onReceivedError(
                                view: WebView?,
                                errorCode: Int,
                                description: String?,
                                failingUrl: String?
                            ) {
                                println("Error loading page: $description")
                                super.onReceivedError(view, errorCode, description, failingUrl)

                            }


                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)
                                println("Error loading page: ${error?.description}")
                            }

                            override fun onReceivedSslError(
                                view: WebView?,
                                handler: SslErrorHandler?,
                                error: SslError?
                            ) {
                                super.onReceivedSslError(view, handler, error)

                                println("onReceivedSslError : $error")

                            }

                            override fun onReceivedHttpError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                errorResponse: WebResourceResponse?
                            ) {
                                super.onReceivedHttpError(view, request, errorResponse)
                                println("HTTP error: ${errorResponse?.statusCode}")
                            }

                            override fun onPageCommitVisible(view: WebView?, url: String?) {
                                println("onPageCommitVisible")
                                println("VIEW : ${view}")
                                super.onPageCommitVisible(view, url)
                            }

                        }



                        webChromeClient = object  : WebChromeClient(){

                            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                                println("onConsoleMessage")
                                return super.onConsoleMessage(consoleMessage)

                            }
                        }

                        // Load the initial URL
                        loadUrl(webViewRoute?.url?:"")
                    }
                },

            )

            if(isPageLoading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = kPrimaryColor
                )
            }




        }
    }
}

@Preview
@Composable
private fun WebViewPagePreview() {
 LtsTheme {
     WebViewPage(navHostController = rememberNavController(), webViewRoute = WebViewRoute(
         title = "Terms-Conditions", url = "https://qa.listentoseniors.com/terms-conditions"
     ))
 }
}