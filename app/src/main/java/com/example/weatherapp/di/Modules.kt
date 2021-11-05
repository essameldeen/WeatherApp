package com.example.weatherapp.di

import com.example.weatherapp.repo.IWeatherRepo
import com.example.weatherapp.repo.WeatherRepo
import com.example.weatherapp.viewModel.WeatherViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL: String = "https://api.openweathermap.org/data/2.5/"

val networkModule = module {

    // provide singleton instance from ApiInterceptor
    single {
        ApiInterceptor()
    }


    // provide singleton instance from HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    // provide singleton instance from okHttpClient
    single {
        provideOkHttpClient(get(), get())
    }

    // provide singleton instance from Retrofit
    single {
        provideRetrofit(get())
    }
    // provide singleton instance from weather apis
    single {
        provideWeatherApi(get())
    }

}


// provide dependency for  repositories Instance
@OptIn(KoinApiExtension::class)
val repoModule = module {
    single<IWeatherRepo> {
        // provide singleton instance from WeatherRepo class
        WeatherRepo(get())
    }
}

// viewModels module used for providing dependencies for viewModels, di used by koin
@OptIn(KoinApiExtension::class)
val viewModelsModules = module {
    // provide viewModel instance from WeatherViewModel class
    viewModel { WeatherViewModel(get()) }
}

fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    apiInterceptor: ApiInterceptor
): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(apiInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideWeatherApi(retrofit: Retrofit)
        : WeatherApis = retrofit.create(WeatherApis::class.java)