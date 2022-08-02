package com.chaudharynabin6.newapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chaudharynabin6.newapp.data.datasources.local.NewsDataBase
import com.chaudharynabin6.newapp.data.datasources.remote.NewsAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesNewsAPI(

    ): NewsAPI {
//        https://stackoverflow.com/questions/70684744/api-call-failed-unable-to-create-converter-for-class-retrofit-moshi
//        https://proandroiddev.com/goodbye-gson-hello-moshi-4e591116231e
        val moshi =
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(NewsAPI.BASE_URL)
            .build()
            .create(NewsAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesNewsDatabase(
        @ApplicationContext context: Context
    ): NewsDataBase {
        return Room.databaseBuilder(
            context,
            NewsDataBase::class.java,
            "news.db",
        ).build()
    }
}