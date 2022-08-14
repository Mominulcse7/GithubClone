package com.mominulcse7.githubclone.features.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepositoryModel {
    var id: Long? = 0L
    var name: String? = ""

    @SerializedName("full_name")
    var fullName: String? = ""

    var description: String? = ""
    var language: String? = ""

    @SerializedName("created_at")
    var createdAt: String? = ""

    @SerializedName("updated_at")
    var updatedAt: String? = ""

    @SerializedName("git_url")
    var gitUrl: String? = ""

    @SerializedName("watchers_count")
    var watchersCount: String? = ""

    @SerializedName("forks_count")
    var forksCount: String? = ""

    @SerializedName("stargazers_count")
    var starCount: String? = ""

    @SerializedName("owner")
    @Expose
    var ownerModel: OwnerModel? = null
}