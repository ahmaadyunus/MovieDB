package com.yunus.moviedb.module

import com.yunus.moviedb.BuildConfig
import com.yunus.moviedb.repository.ServiceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { provideGsonConverter() }
    single { provideRetrofit(get(), get()) }
    single { provideApi(get()) }
    single { provideOkHttpClient() }

}

fun provideGsonConverter(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

fun provideRetrofit(gsonConverter: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(gsonConverter)
        .build()
}

fun provideApi(retrofit: Retrofit): ServiceApi {
    return retrofit.create(ServiceApi::class.java)
}

fun provideOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(logging)
    }

    okHttpClientBuilder.connectTimeout(1, TimeUnit.MINUTES)
    okHttpClientBuilder.readTimeout(1, TimeUnit.MINUTES)

    return okHttpClientBuilder.build()
}