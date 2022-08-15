package com.mominulcse7.githubclone.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class RepositoryModel {

    @PrimaryKey(autoGenerate = false)
    var id: Long? = 0L
    var name: String? = ""

    @SerializedName("full_name")
    var full_name: String? = ""

    var description: String? = ""
    var language: String? = ""

    @SerializedName("created_at")
    var created_at: String? = ""

    @SerializedName("updated_at")
    var updated_at: String? = ""

    @SerializedName("git_url")
    var git_url: String? = ""

    @SerializedName("watchers_count")
    var watchers_count: Long? = 0L

    @SerializedName("forks_count")
    var forks_count: Long? = 0L

    @SerializedName("stargazers_count")
    var stargazers_count: Long? = 0L

    @SerializedName("owner")
    @Embedded(prefix = "owner_")
    var ownerModel: OwnerModel? = null
}