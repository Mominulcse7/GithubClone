package com.mominulcse7.githubclone.features.viewModel

import androidx.lifecycle.MutableLiveData
import com.mominulcse7.githubclone.features.model.RepositoryModelResponse
import com.mominulcse7.githubclone.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {

    val liveRepositoryModelResponse = MutableLiveData<RepositoryModelResponse>()

    fun getRepoList(searchKey: String, pageNumber: Int, perPage: Int, orderBy: String, sortBy: String) {
        getResponse(apiService::getRepositoryList, searchKey, pageNumber.toString(), perPage.toString(), orderBy, sortBy) {
            liveRepositoryModelResponse.value = it
        }
    }
}