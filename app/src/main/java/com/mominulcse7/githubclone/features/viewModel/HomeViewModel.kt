package com.mominulcse7.githubclone.features.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mominulcse7.githubclone.db.GithubDB
import com.mominulcse7.githubclone.features.model.RepositoryModel
import com.mominulcse7.githubclone.features.model.SearchUrlModel
import com.mominulcse7.githubclone.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val githubDB: GithubDB
) : BaseViewModel() {

    val liveRepositoryModelResponse = MutableLiveData<ArrayList<RepositoryModel>>()
    val liveTotalItemCount = MutableLiveData<Long>()

    fun getRepoList(sModel: SearchUrlModel) {
        viewModelScope.launch {
            val listRepo =
                githubDB.getGithubDao().getRepositoryListDB(
                    sModel.searchKey,
                    sModel.currentPage.toString(),
                    sModel.itemPerPage.toString(),
                    sModel.sortBy,
                    sModel.orderBy
                )

            liveRepositoryModelResponse.value = listRepo
            liveTotalItemCount.value = listRepo.size.toLong()
        }

        getResponse(
            apiService::getRepositoryList,
            sModel.searchKey,
            sModel.currentPage.toString(),
            sModel.itemPerPage.toString(),
            sModel.sortBy,
            sModel.orderBy
        ) {
            viewModelScope.launch {
                githubDB.getGithubDao().addGithubRepoList(it.listRepo!!)
            }
            liveRepositoryModelResponse.value = it.listRepo
            liveTotalItemCount.value = it.totalCount
        }
    }
}