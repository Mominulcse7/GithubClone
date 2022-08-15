package com.mominulcse7.githubclone.network

import com.mominulcse7.githubclone.model.RepositoryModelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    suspend fun getRepositoryList(
        @Query("q") searchKey: String,
        @Query("page") pageNumber: String,
        @Query("per_page") perPage: String,
        @Query("s") sortBy: String,
        @Query("o") orderBy: String
    ): RepositoryModelResponse
}