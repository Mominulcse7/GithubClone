package com.mominulcse7.githubclone.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mominulcse7.githubclone.model.OwnerModel
import com.mominulcse7.githubclone.model.RepositoryModel
import com.mominulcse7.githubclone.utils.ConstantKeys.ROOM_DB_VERSION

@Database(entities = [RepositoryModel::class, OwnerModel::class], version = ROOM_DB_VERSION)
abstract class GithubDB :RoomDatabase(){

    abstract fun getGithubDao(): GithubDao
}