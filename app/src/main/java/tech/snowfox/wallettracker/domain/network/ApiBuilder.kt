package tech.snowfox.wallettracker.domain.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.snowfox.wallettracker.BuildConfig
import tech.snowfox.wallettracker.domain.network.factory.LiveDataCallAdapterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://wtracker-staging.icobox.io/v1/"
private const val TIMEOUT_REQUEST = 30 * 1000L

object ApiBuilder {

    fun build(): ApiService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) builder.addInterceptor(httpLoggingInterceptor)
        builder.connectTimeout(TIMEOUT_REQUEST, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT_REQUEST, TimeUnit.MILLISECONDS)
                .build()

        return builder.build()
    }
}