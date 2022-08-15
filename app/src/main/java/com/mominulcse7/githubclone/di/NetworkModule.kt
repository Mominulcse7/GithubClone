package com.mominulcse7.githubclone.di


import com.mominulcse7.githubclone.BuildConfig
import com.mominulcse7.githubclone.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun providesRetrofit(httpLoggingInterceptor: HttpLoggingInterceptor): Retrofit {

        val httpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun printApiReqResponse(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message: String? ->
            if (BuildConfig.DEBUG)
                println(message)
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun providesUserAPI(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}