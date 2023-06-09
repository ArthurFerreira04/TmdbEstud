package com.example.tmdbestud.di

import com.example.tmdbestud.api.request.MoviesApi
import com.example.tmdbestud.view.adapter.listeners.MoviesRecycler
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
    }

    single<Interceptor>(named("INTERCEPTOR")) {
        Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url

            val url: HttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", "8f9e12707813ac21626ea699edc4b71e")
                .addQueryParameter("language", "pt-BR")
                .build()
            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    single {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>(named("INTERCEPTOR")))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        get<Retrofit>().create(MoviesApi::class.java)
    }

    factory {
        MoviesRecycler()
    }
}
