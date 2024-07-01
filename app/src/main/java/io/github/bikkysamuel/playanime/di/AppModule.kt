package io.github.bikkysamuel.playanime.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.bikkysamuel.playanime.localstorage.db.PlayAnimeDatabase
import io.github.bikkysamuel.playanime.network.PlayAnimeApi
import io.github.bikkysamuel.playanime.network.PlayAnimeApiRepository
import io.github.bikkysamuel.playanime.utils.Constants
import io.github.bikkysamuel.playanime.utils.Constants.PLAY_ANIME_DATABASE
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Room
    @Singleton
    @Provides
    fun providePlayAnimeDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PlayAnimeDatabase::class.java,
        PLAY_ANIME_DATABASE
    ).build()

    @Singleton
    @Provides
    fun providePlayAnimeDao(db: PlayAnimeDatabase) = db.getPlayAnimeDao()

    //Retrofit
    @Singleton
    @Provides
    fun providePlayAnimeApiRepository(
        api: PlayAnimeApi
    ) = PlayAnimeApiRepository(api)

    @Singleton
    @Provides
    fun providePlayAnimeApi(): PlayAnimeApi {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PlayAnimeApi::class.java)
    }
}