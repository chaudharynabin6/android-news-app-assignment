package com.chaudharynabin6.newapp.other.network.okhttp.interceptors

import android.content.Context
import android.widget.Toast
import com.chaudharynabin6.newapp.other.utils.ShowToast
import com.chuckerteam.chucker.api.ChuckerCollector
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import java.io.IOException
import java.lang.String

internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        logger.info(
            String.format(
                "Sending request %s on %s%n%s",
                request.url, chain.connection(), request.headers
            )
        )
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        logger.info(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request.url, (t2 - t1) / 1e6, response.headers
            )
        )
        return response
    }
}

internal class ErrorHandlerInterceptor(val context: Context, val showToast: ShowToast) :
    Interceptor {

    @Throws(IOException::class)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            when (response.code) {
                in 401 until 403 -> {

                    showToast.showToast("Error with code : ${response.code} ")

                }
                else -> {
                    throw IOException("Error in Response")
                }
            }
        }

        return response
    }
}

