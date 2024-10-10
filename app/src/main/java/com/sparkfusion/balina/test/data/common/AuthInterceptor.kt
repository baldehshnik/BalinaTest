package com.sparkfusion.balina.test.data.common

import com.sparkfusion.balina.test.data.datastore.TokenCache
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenCache: TokenCache
) : Interceptor {

    private val noAuthRequired = listOf("/api/account/signin", "/api/account/signup")

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (noAuthRequired.any { originalRequest.url.encodedPath.contains(it) }) {
            return chain.proceed(originalRequest)
        }

        val token = runBlocking { tokenCache.getToken() }
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val requestWithToken = originalRequest.newBuilder()
            .url(originalRequest.url)
            .addHeader("Access-Token", token)
            .build()

        return chain.proceed(requestWithToken)
    }
}