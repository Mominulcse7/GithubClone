package com.mominulcse7.githubclone.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepositoryModelResponse {

    @SerializedName("total_count")
    @Expose
    var totalCount: Long = 0L

    @SerializedName("items")
    @Expose
    var listRepo: ArrayList<RepositoryModel>? = ArrayList()
}