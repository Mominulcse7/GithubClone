package com.mominulcse7.githubclone.features.viewModel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mominulcse7.githubclone.db.GithubDB
import com.mominulcse7.githubclone.features.model.RepositoryModel
import com.mominulcse7.githubclone.features.model.SearchUrlModel
import com.mominulcse7.githubclone.network.ApiService
import com.mominulcse7.githubclone.utils.haveNetwork
import com.mominulcse7.githubclone.utils.logPrint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val apiService: ApiService,
    private val githubDB: GithubDB
) : BaseViewModel() {

    val context = ctx
    val liveRepositoryModelResponse = MutableLiveData<ArrayList<RepositoryModel>>()
    val liveTotalItemCount = MutableLiveData<Long>()

    fun getRepoList(sModel: SearchUrlModel) {
        if (context.haveNetwork()) {
            getResponse(
                apiService::getRepositoryList,
                sModel.searchKey,
                sModel.currentPage.toString(),
                sModel.itemPerPage.toString(),
                sModel.sortBy,
                sModel.orderBy
            ) {
                //save list in room db
                viewModelScope.launch {
                    githubDB.getGithubDao().addGithubRepoList(it.listRepo!!)
                }
                liveRepositoryModelResponse.value = it.listRepo
                liveTotalItemCount.value = it.totalCount
            }
        } else {

            viewModelScope.launch {
                val listRepo =
                    githubDB.getGithubDao().getRepositoryListDB(
                        sModel.searchKey,
                        sModel.sortBy,
                        sModel.orderBy
                    )

                liveRepositoryModelResponse.value = (listRepo as ArrayList<RepositoryModel>?)!!
                liveTotalItemCount.value = listRepo?.size?.toLong()
            }
        }
    }
}