package com.mominulcse7.githubclone.features.viewModel

import androidx.lifecycle.MutableLiveData
import com.mominulcse7.githubclone.features.model.RepositoryModelResponse
import com.mominulcse7.githubclone.features.model.SearchUrlModel
import com.mominulcse7.githubclone.network.ApiService
import com.mominulcse7.githubclone.utils.logPrint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {

    val liveRepositoryModelResponse = MutableLiveData<RepositoryModelResponse>()

    fun getRepoList(sModel: SearchUrlModel) {
        logPrint(sModel)
        getResponse(
            apiService::getRepositoryList,
            sModel.searchKey,
            sModel.currentPage.toString(),
            sModel.itemPerPage.toString(),
            sModel.sortBy,
            sModel.orderBy
        ) {
            liveRepositoryModelResponse.value = it
        }
    }
}