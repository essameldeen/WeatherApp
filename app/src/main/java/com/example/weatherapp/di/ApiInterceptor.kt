package com.example.weatherapp.di

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY: String = "5116adefd6f9805b5478ddb9e4e3fcab"

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var newRequest = chain.request()

        val url: HttpUrl = newRequest.url.newBuilder()
            .addQueryParameter("appid", API_KEY).build()
        newRequest = newRequest.newBuilder().url(url).build()
        return  chain.proceed(newRequest)
    }
}