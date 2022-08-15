package com.mominulcse7.githubclone.features.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class OwnerModel {

    @PrimaryKey(autoGenerate = false)
    var id: Long? = 0L

    @SerializedName("login")
    var name: String? = ""

    var description: String? = ""
    var bio: String? = ""

    @SerializedName("repos_url")
    var reposUrl: String? = ""

    @SerializedName("avatar_url")
    var avatarUrl: String? = ""

    @SerializedName("public_repos")
    var publicRepos: String? = ""

    @SerializedName("git_url")
    var gitUrl: String? = ""

    @SerializedName("created_at")
    var createdAt: String? = ""

    @SerializedName("updated_at")
    var updatedAt: String? = ""
}