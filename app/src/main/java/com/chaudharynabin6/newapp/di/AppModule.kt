package com.chaudharynabin6.newapp.di


import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.data.datasources.local.NewsDataBase
import com.chaudharynabin6.newapp.data.datasources.remote.NewsAPI
import com.chaudharynabin6.newapp.other.AppConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
        @ApplicationContext context: Context,
    ): NewsDataBase {
        return Room.databaseBuilder(
            context,
            NewsDataBase::class.java,
            "news.db",
        ).build()
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context,
    ): RequestManager {
        val animPlaceholder =
            ContextCompat.getDrawable(context, R.drawable.loading_placeholder) as AnimationDrawable
            animPlaceholder.start()





        return Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(animPlaceholder)
                .error(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)


        )
    }

    @Provides
    fun providesDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Singleton
    @Provides
    fun providesSharedPreference(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
    }
}