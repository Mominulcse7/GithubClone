package com.mominulcse7.githubclone.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mominulcse7.githubclone.features.model.OwnerModel
import com.mominulcse7.githubclone.features.model.RepositoryModel

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGithubRepoList(listRepo: List<RepositoryModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepoOwner(ownerModel: OwnerModel)

    @Query(
        "SELECT * FROM RepositoryModel WHERE full_name LIKE '%' || :searchKey || '%' ORDER BY "
                + " CASE WHEN ( :orderBy = 'asc' AND :sortBy= 'stars') THEN stargazers_count END ASC, "
                + " CASE WHEN ( :orderBy = 'desc' AND :sortBy= 'stars') THEN stargazers_count END DESC, "
                + " CASE WHEN ( :orderBy = 'asc' AND :sortBy= 'forks') THEN forks_count END ASC, "
                + " CASE WHEN ( :orderBy = 'desc' AND :sortBy= 'forks') THEN forks_count END DESC, "
                + " CASE WHEN ( :orderBy = 'asc' AND :sortBy= 'updated') THEN updated_at END ASC, "
                + " CASE WHEN ( :orderBy = 'desc' AND :sortBy= 'updated') THEN updated_at END DESC "
    )
    suspend fun getRepositoryListDB(
        searchKey: String,
        sortBy: String,
        orderBy: String
    ): List<RepositoryModel>?

}