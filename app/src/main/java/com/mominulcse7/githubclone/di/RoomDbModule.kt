package com.mominulcse7.githubclone.di

import android.content.Context
import androidx.room.Room
import com.mominulcse7.githubclone.db.GithubDB
import com.mominulcse7.githubclone.db.GithubDao
import com.mominulcse7.githubclone.utils.ConstantKeys.ROOM_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RoomDbModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDB {
        return Room.databaseBuilder(context, GithubDB::class.java, ROOM_DB_NAME).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideGithubDao(githubDB: GithubDB): GithubDao {
        return githubDB.getGithubDao()
    }
}