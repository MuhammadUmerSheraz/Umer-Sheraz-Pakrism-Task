package umer.task.pakrism.di

import android.content.Context
import umer.task.pakrism.remoteSource.ApiInterface
import umer.task.pakrism.repositories.RepositoryData
import umer.task.pakrism.ui.MovieViewModel
import umer.task.pakrism.utils.NetworkCheck
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Modules {
    private const val BASE_URL = "https://api.themoviedb.org"
     const val BASE_IMAGE_URL ="https://image.tmdb.org/t/p/w300"

    val appModules = module {

        single {
            createWebService<ApiInterface>(
                okHttpClient = createHttpClient(),
                factory = RxJava2CallAdapterFactory.create(),
                baseUrl = BASE_URL
            )
        }


        factory {
            NetworkCheck(get())
        }
        factory {
            RepositoryData(get(),get())
        }
    }
    val viewModelModules = module {
        viewModel { MovieViewModel(get(),get()) }

    }

    private fun getModules(): List<Module> {
        val list: ArrayList<Module> = ArrayList()
        list.add(appModules)
        list.add(viewModelModules)
        return list
    }

    fun init(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(getModules())
        }
    }

    inline fun <reified T> createWebService(
        okHttpClient: OkHttpClient,
        factory: CallAdapter.Factory, baseUrl: String
    ): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(factory)
            .client(okHttpClient)
            .build()
        return retrofit.create(T::class.java)
    }

    fun createHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
    }


}