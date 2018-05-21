package tech.snowfox.wallettracker.domain.network

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.snowfox.wallettracker.BuildConfig
import tech.snowfox.wallettracker.domain.network.factory.LiveDataCallAdapterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://wtracker-staging.icobox.io/v1/"
private const val TIMEOUT_REQUEST = 30 * 1000L

object ApiBuilder {

    fun build(context: Context): ApiService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
    }

    private fun createOkHttpClient(context: Context): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) builder.addInterceptor(ChuckInterceptor(context))
        builder.connectTimeout(TIMEOUT_REQUEST, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT_REQUEST, TimeUnit.MILLISECONDS)
                .build()

        return builder.build()
    }
}