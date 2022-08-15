package com.mominulcse7.githubclone.view.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mominulcse7.githubclone.R
import com.mominulcse7.githubclone.databinding.FragmentRepoListBinding
import com.mominulcse7.githubclone.model.RepositoryModel
import com.mominulcse7.githubclone.model.SearchUrlModel
import com.mominulcse7.githubclone.view.adapters.RepoListAdapter
import com.mominulcse7.githubclone.viewModel.HomeViewModel
import com.mominulcse7.githubclone.sessions.TempSharePref
import com.mominulcse7.githubclone.utils.ConstantKeys.INTENT_DATA
import com.mominulcse7.githubclone.utils.DotsProgressBar.DDProgressBarDialog
import com.mominulcse7.githubclone.utils.closeKeyboard
import com.mominulcse7.githubclone.utils.setToolbarTitle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RepoListFragment : Fragment() {

    private lateinit var pDialog: DDProgressBarDialog
    private lateinit var activity: Activity
    private lateinit var binding: FragmentRepoListBinding

    private lateinit var adapter: RepoListAdapter
    private var recyclerView: RecyclerView? = null
    private var listRepo = ArrayList<RepositoryModel>()

    private var searchModel = SearchUrlModel()
    private var msg = ""

    @Inject
    lateinit var tempSharePref: TempSharePref

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity()
        pDialog = DDProgressBarDialog(activity)
        viewModel.application = activity.application
        loadFirstList()
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = FragmentRepoListBinding.inflate(lif, vg, false)
        activity.setToolbarTitle(activity.resources.getString(R.string.app_name))
        initiateView()
        createMenu()
        observeViewModel()
        return binding.root
    }

    private fun initiateView() {

        recyclerView = binding.recyclerView
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (searchModel.totalPage > searchModel.currentPage
                        && searchModel.totalItem > listRepo.size
                    ) {
                        loadList()
                    }
                }
            }
        })

    }

    private fun observeViewModel() {
        viewModel.liveTotalItemCount.observe(viewLifecycleOwner) {
            searchModel.totalItem = it
            searchModel.totalPage = it / searchModel.itemPerPage + 1
        }

        viewModel.liveRepositoryModelResponse.observe(viewLifecycleOwner) {
            pDialog.dismiss()
            msg = activity.resources.getString(R.string.no_repository_found)
            if (searchModel.currentPage <= 1L)
                listRepo = it
            else
                listRepo.addAll(it)

            setList()
        }
    }

    private fun loadFirstList() {
        listRepo = ArrayList()

        searchModel.currentPage = 0
        searchModel.totalPage = 1L
        searchModel.sortBy = tempSharePref.getSortBy()!!
        searchModel.orderBy = tempSharePref.getOrderBy()!!
        loadList()
    }

    private fun loadList() {
        msg = activity.resources.getString(R.string.loading_data_plz_wait)
        pDialog.show()
        searchModel.currentPage = searchModel.currentPage + 1
        viewModel.getRepoList(searchModel)
    }

    private fun setList() {
        activity.closeKeyboard()

        if (listRepo.size > 0) {
            binding.llNoData.visibility = View.GONE
        } else {
            binding.llNoData.visibility = View.VISIBLE
            binding.tvNoDataTitle.text = msg
        }
        if (searchModel.currentPage == 1) {
            adapter = RepoListAdapter(::onDetailsClicked)
            recyclerView?.adapter = adapter
        }
        adapter.setList(listRepo)
        adapter.notifyDataSetChanged()
    }

    private fun onDetailsClicked(cModel: RepositoryModel) {
        val bundle = Bundle()
        bundle.putString(INTENT_DATA, Gson().toJson(cModel))
        findNavController().navigate(R.id.repoDetailsFragment, bundle)
    }

    private fun createMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_filter, menu)
                menu.findItem(R.id.mSearch).isVisible = true
                menu.findItem(R.id.mSort).isVisible = true

                val searchView = menu.findItem(R.id.mSearch).actionView as SearchView
                searchView.queryHint = requireActivity().resources.getString(R.string.search)
                searchView.isSubmitButtonEnabled = false
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(s: String): Boolean {
                        searchModel.searchKey = s
                        loadFirstList()
                        return false
                    }

                    override fun onQueryTextChange(s: String): Boolean {
//                        searchModel.searchKey = s
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.sByMostStar -> {
                        tempSharePref.saveSortBy("stars")
                        tempSharePref.saveOrderBy("desc")
                        loadFirstList()
                    }
                    R.id.sByFewStar -> {
                        tempSharePref.saveSortBy("stars")
                        tempSharePref.saveOrderBy("asc")
                        loadFirstList()
                    }
                    R.id.sByMostFork -> {
                        tempSharePref.saveSortBy("forks")
                        tempSharePref.saveOrderBy("desc")
                        loadFirstList()
                    }
                    R.id.sByFewFork -> {
                        tempSharePref.saveSortBy("forks")
                        tempSharePref.saveOrderBy("asc")
                        loadFirstList()
                    }
                    R.id.sByRecentUpdate -> {
                        tempSharePref.saveSortBy("updated")
                        tempSharePref.saveOrderBy("desc")
                        loadFirstList()
                    }
                    R.id.sByLeastRecentUpdate -> {
                        tempSharePref.saveSortBy("updated")
                        tempSharePref.saveOrderBy("asc")
                        loadFirstList()
                    }
                }

                activity.closeKeyboard()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }
}