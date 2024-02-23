package com.pedropelayo.prueba_tecnica.data.remote.config

import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val rawJson = response.body?.string()

        // Imprime el JSON en la consola de depuración
        println("Response JSON: $rawJson")

        // Reconstruye el cuerpo de la respuesta para que pueda ser consumido nuevamente
        return response.newBuilder()
            .body(okhttp3.ResponseBody.create(response.body?.contentType(), rawJson ?: ""))
            .build()
    }
}