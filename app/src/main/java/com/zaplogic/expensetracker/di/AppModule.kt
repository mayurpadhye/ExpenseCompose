package com.zaplogic.expensetracker.di

import com.zaplogic.expensetracker.data.remote.api.ApiInterface
import com.zaplogic.expensetracker.domain.repository.ExpensesRepository
import com.zaplogic.expensetracker.domain.repository.AuthRepository
import com.zaplogic.expensetracker.data.repositoryImpl.AuthRepositoryImpl
import com.zaplogic.expensetracker.data.repositoryImpl.ExpensesRepositoryImpl
import com.zaplogic.expensetracker.core.events.SessionEvents
import com.zaplogic.expensetracker.data.repositoryImpl.PreferenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionEvents() = SessionEvents()

    @Provides
    @Singleton
    fun provideLoginRepository(apiInterface: ApiInterface): AuthRepository {
        return AuthRepositoryImpl(apiInterface)
    }

    @Provides
    @Singleton
    fun provideExpensesRepository(
        repository: ExpensesRepositoryImpl
    ): ExpensesRepository = repository

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        sharedPreference: PreferenceRepositoryImpl,
        sessionEvents: SessionEvents
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val token = sharedPreference.getAccessToken()
                if (!token.isNullOrEmpty()) {
                    request.addHeader("Authorization", "Bearer $token")
                }
                val res = chain.proceed(request.build())
                if (res.code == 401) {
                    sessionEvents.triggerLogout()
                    sharedPreference.clear()
                }
                res
            }
            .build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/v1/expense-management/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)

    }

}