package com.mominulcse7.githubclone.network

import com.mominulcse7.githubclone.features.model.RepositoryModelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories?type=Repositories")
    suspend fun getRepositoryList(
        @Query("q") searchKey: String,
        @Query("page") pageNumber: String,
        @Query("per_page") perPage: String,
        @Query("o") orderBy: String,
        @Query("s") sortBy: String,
    ): RepositoryModelResponse
}