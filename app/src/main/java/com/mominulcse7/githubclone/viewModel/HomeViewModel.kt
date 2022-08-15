package com.mominulcse7.githubclone.viewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mominulcse7.githubclone.db.GithubDB
import com.mominulcse7.githubclone.db.GithubDao
import com.mominulcse7.githubclone.model.RepositoryModel
import com.mominulcse7.githubclone.model.SearchUrlModel
import com.mominulcse7.githubclone.network.ApiService
import com.mominulcse7.githubclone.utils.haveNetwork
import com.mominulcse7.githubclone.utils.logPrint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ctx: Application,
    private val apiService: ApiService,
    private val githubDao: GithubDao
) : BaseViewModel() {
    val liveRepositoryModelResponse = MutableLiveData<ArrayList<RepositoryModel>>()
    val liveTotalItemCount = MutableLiveData<Long>()

    fun getRepoList(sModel: SearchUrlModel) {
//        if (ctx.haveNetwork()) {
//            getResponse(
//                apiService::getRepositoryList,
//                sModel.searchKey,
//                sModel.currentPage.toString(),
//                sModel.itemPerPage.toString(),
//                sModel.sortBy,
//                sModel.orderBy
//            ) {
//                //save list in room db
//                viewModelScope.launch {
//                    githubDB.getGithubDao().addGithubRepoList(it.listRepo!!)
//                }
//                liveTotalItemCount.value = it.totalCount
//                liveRepositoryModelResponse.value = it.listRepo
//            }
//        } else {

            viewModelScope.launch {
                val listRepo =
                    githubDao.getRepositoryListDB(
                        sModel.searchKey,
                        sModel.sortBy,
                        sModel.orderBy
                    )

                liveTotalItemCount.value = listRepo?.size?.toLong()
                liveRepositoryModelResponse.value = (listRepo as ArrayList<RepositoryModel>?)!!
//            }
        }
    }
}