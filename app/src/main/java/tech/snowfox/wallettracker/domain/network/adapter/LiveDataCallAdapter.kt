package tech.snowfox.wallettracker.domain.network.adapter

import android.arch.lifecycle.LiveData
import retrofit2.*
import tech.snowfox.wallettracker.data.dto.Data
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T>(
        private val retrofit: Retrofit,
        private val responseType: Type
) : CallAdapter<T, LiveData<Data<T>>> {

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
                            postValue(Data.create(retrofit, response))
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
