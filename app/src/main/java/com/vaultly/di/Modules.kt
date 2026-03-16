package com.vaultly.di

import android.content.Context
import androidx.room.Room
import com.vaultly.data.local.AppDatabase
import com.vaultly.data.local.dao.TransactionDao
import com.vaultly.data.repository.TransactionRepositoryImpl
import com.vaultly.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "vaultly.db").fallbackToDestructiveMigration().build()

    @Provides fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://example.supabase.co/")
        .client(client)
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository = impl
}

@Module
@InstallIn(SingletonComponent::class)
object PlaidModule

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule
