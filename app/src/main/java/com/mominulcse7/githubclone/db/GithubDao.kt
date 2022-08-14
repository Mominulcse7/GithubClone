package com.mominulcse7.githubclone.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mominulcse7.githubclone.features.model.RepositoryModel

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGithubRepoList(listRepo: ArrayList<RepositoryModel>)

    @Query(
        "SELECT * FROM RepositoryModel WHERE language LIKE :searchKey ORDER BY " +
                "CASE WHEN ( :sortBy = 'asc' AND :orderBy= 'stars') THEN starCount END ASC, "+
                "CASE WHEN ( :sortBy = 'desc' AND :orderBy= 'stars') THEN starCount END DESC, "+
                "CASE WHEN ( :sortBy = 'asc' AND :orderBy= 'forks') THEN forksCount END ASC, "+
                "CASE WHEN ( :sortBy = 'desc' AND :orderBy= 'forks') THEN forksCount END DESC, "+
                "CASE WHEN ( :sortBy = 'asc' AND :orderBy= 'updated') THEN updatedAt END ASC, "+
                "CASE WHEN ( :sortBy = 'desc' AND :orderBy= 'updated') THEN updatedAt END DESC "
    )
    suspend fun getRepositoryListDB(
        searchKey: String,
        pageNumber: String,
        perPage: String,
        sortBy: String,
        orderBy: String
    ): ArrayList<RepositoryModel>

}