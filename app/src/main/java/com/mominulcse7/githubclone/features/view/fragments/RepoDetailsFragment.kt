package com.mominulcse7.githubclone.features.view.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mominulcse7.githubclone.R
import com.mominulcse7.githubclone.databinding.FragmentRepoDetailsBinding
import com.mominulcse7.githubclone.utils.DotsProgressBar.DDProgressBarDialog
import com.mominulcse7.githubclone.utils.setToolbarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private lateinit var pDialog: DDProgressBarDialog
    private lateinit var activity: Activity
    private lateinit var binding: FragmentRepoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity()
        pDialog = DDProgressBarDialog(activity)
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = DataBindingUtil.inflate(lif, R.layout.fragment_repo_details, vg, false)
        activity.setToolbarTitle(activity.resources.getString(R.string.repo_details))
        initiateView()
        observeViewModel()
        return binding.root
    }

    private fun initiateView() {
    }

    private fun observeViewModel() {

    }
}