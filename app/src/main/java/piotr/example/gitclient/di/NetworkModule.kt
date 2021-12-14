package piotr.example.gitclient.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import piotr.example.data.api.GitApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson

import com.google.gson.GsonBuilder
import piotr.example.domain.Constants


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return  HttpLoggingInterceptor().apply { this.level= HttpLoggingInterceptor.Level.BODY }
    }
    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor:HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(Constants.DATE_FORMAT)
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient:OkHttpClient, gson:Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideGitApi(retrofit : Retrofit) : GitApi {
        return  retrofit.create(GitApi::class.java)
    }


}