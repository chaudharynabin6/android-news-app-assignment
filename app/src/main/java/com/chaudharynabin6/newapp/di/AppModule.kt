package com.chaudharynabin6.newapp.di


import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.data.datasources.local.NewsDataBase
import com.chaudharynabin6.newapp.data.datasources.remote.NewsAPI
import com.chaudharynabin6.newapp.other.AppConstants
import com.chaudharynabin6.newapp.other.network.okhttp.interceptors.ErrorHandlerInterceptor
import com.chaudharynabin6.newapp.other.utils.ShowToast
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesOkhttpClient(
        @ApplicationContext context: Context,
        showToast: ShowToast,
    ): OkHttpClient {
        // Create the Collector
        val chuckerCollector = ChuckerCollector(
            context = context,
            // Toggles visibility of the notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
            .addInterceptor(ErrorHandlerInterceptor(context = context, showToast = showToast))
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(chuckerCollector)
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }


    @Singleton
    @Provides
    fun provideShowToast(
        @ApplicationContext context: Context,
    ): ShowToast {
        return ShowToast(context)
    }

    @Singleton
    @Provides
    fun providesNewsAPI(
        client: OkHttpClient,
    ): NewsAPI {
//        https://stackoverflow.com/questions/70684744/api-call-failed-unable-to-create-converter-for-class-retrofit-moshi
//        https://proandroiddev.com/goodbye-gson-hello-moshi-4e591116231e
        val moshi =
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        return Retrofit.Builder()
            .client(client)
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

        return Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.rotated_progress_bar)
                .error(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
        return context.getSharedPreferences(
            AppConstants.SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }
}