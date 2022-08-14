package com.mominulcse7.githubclone.features.view.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mominulcse7.githubclone.R
import com.mominulcse7.githubclone.databinding.FragmentRepoListBinding
import com.mominulcse7.githubclone.features.model.RepositoryModel
import com.mominulcse7.githubclone.features.view.adapters.RepoListAdapter
import com.mominulcse7.githubclone.features.viewModel.HomeViewModel
import com.mominulcse7.githubclone.utils.ConstantKeys.INTENT_DATA
import com.mominulcse7.githubclone.utils.DotsProgressBar.DDProgressBarDialog
import com.mominulcse7.githubclone.utils.closeKeyboard
import com.mominulcse7.githubclone.utils.logPrint
import com.mominulcse7.githubclone.utils.setToolbarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : Fragment(), View.OnClickListener, RepoListAdapter.RepoListAdapterListener {

    private lateinit var pDialog: DDProgressBarDialog
    private lateinit var activity: Activity
    private lateinit var fragment: Fragment
    private lateinit var binding: FragmentRepoListBinding

    private lateinit var adapter: RepoListAdapter
    private var recyclerView: RecyclerView? = null

    private var listRepo = ArrayList<RepositoryModel>()

    private val viewModel by viewModels<HomeViewModel>()

    private var searchKey = "android"
    private var currentPage = 1
    private var itemPerPage = 50
    private var sortBy = "star"
    private var orderBy = "asc"
    private var totalPage = 0L
    private var msg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity()
        fragment = this
        pDialog = DDProgressBarDialog(activity)

        loadFirstList()
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = DataBindingUtil.inflate(lif, R.layout.fragment_repo_list, vg, false)
        activity.setToolbarTitle(activity.resources.getString(R.string.app_name))
        initiateView()
        observeViewModel()
        return binding.root
    }

    private fun initiateView() {

        recyclerView = binding.recyclerView
        recyclerView?.setHasFixedSize(true)
        adapter = RepoListAdapter(listRepo, fragment, activity)
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (totalPage > currentPage) {
                        loadList()
                    }
                }
            }
        })

        binding.vRoot.setOnClickListener(this)
    }

    private fun observeViewModel() {
        viewModel.liveRepositoryModelResponse.observe(viewLifecycleOwner) {
            pDialog.dismiss()
            if (it != null) {
                msg = activity.resources.getString(R.string.no_repository_found)

                if (it.listRepo != null && it.listRepo?.size!! > 0) {

                    if (currentPage <= 1L)
                        listRepo = it.listRepo!!
                    else
                        listRepo.addAll(it.listRepo!!)

                    try {
                        totalPage = it.totalCount / itemPerPage + 1
                    } catch (e: Exception) {
                    }
                }
            }
            setList()
        }
    }

    private fun loadFirstList() {

        msg = activity.resources.getString(R.string.loading_data_plz_wait)
        listRepo = ArrayList()
        totalPage = 1
        currentPage = 0
        itemPerPage = 50
        loadList()
    }

    private fun loadList() {
        msg = activity.resources.getString(R.string.loading_data_plz_wait)
        pDialog.show()
        currentPage++
        viewModel.getRepoList(searchKey, currentPage, itemPerPage,  orderBy,sortBy )
    }

    private fun setList() {
        if (listRepo.size > 0) {
            binding.llNoData.visibility = View.GONE
        } else {
            binding.llNoData.visibility = View.VISIBLE
            binding.tvNoDataTitle.text = msg
        }

        adapter.setItems(listRepo)
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        activity.closeKeyboard()

        when (v.id) {
            R.id.vRoot -> {
//                findNavController().navigate(R.id.repoDetailsFragment)
            }
        }
    }

    override fun onDetailsView(item: View, cModel: RepositoryModel, position: Int) {
        val bundle = Bundle()
        bundle.putString(INTENT_DATA, Gson().toJson(cModel))
        findNavController().navigate(R.id.repoDetailsFragment, bundle)
    }
}