package tech.snowfox.wallettracker.domain.network.adapter

import android.arch.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import tech.snowfox.wallettracker.data.dto.Data
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<Data<T>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>): LiveData<Data<T>> {
        return object : LiveData<Data<T>>() {

            internal var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {

                    call.enqueue(object : Callback<T> {

                        override fun onResponse(call: Call<T>?, response: Response<T>?) {
                            val body = response?.body()
                            postValue(when (body) {
                                null -> Data.error(code = response?.code())
                                else -> Data.success(body = body)
                            })
                        }

                        override fun onFailure(call: Call<T>, throwable: Throwable) {
                            postValue(Data.error(throwable.message))
                        }
                    })
                }
            }
        }
    }
}
