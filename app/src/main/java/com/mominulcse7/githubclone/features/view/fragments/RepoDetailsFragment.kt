package com.mominulcse7.githubclone.features.view.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.mominulcse7.githubclone.R
import com.mominulcse7.githubclone.databinding.FragmentRepoDetailsBinding
import com.mominulcse7.githubclone.databinding.FragmentRepoListBinding
import com.mominulcse7.githubclone.features.model.RepositoryModel
import com.mominulcse7.githubclone.utils.ConstantKeys.INTENT_DATA
import com.mominulcse7.githubclone.utils.DotsProgressBar.DDProgressBarDialog
import com.mominulcse7.githubclone.utils.setToolbarTitle
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private lateinit var activity: Activity
    private lateinit var binding: FragmentRepoDetailsBinding
    private var repoModel: RepositoryModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity()

        if (arguments != null) {
            repoModel = try {
                Gson().fromJson(
                    requireArguments().getString(INTENT_DATA),
                    RepositoryModel::class.java
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = FragmentRepoDetailsBinding.inflate(lif, vg, false)
        activity.setToolbarTitle(activity.resources.getString(R.string.repo_details))
        initiateView()
        setData()
        return binding.root
    }

    private fun initiateView() {
    }

    private fun setData() {

        if (repoModel != null) {
            binding.tvOwnerName.text = repoModel?.ownerModel?.name
            binding.tvOwnerDescription.text = repoModel?.ownerModel?.description
            binding.tvRepoName.text = repoModel?.fullName
            binding.tvDescription.text = repoModel?.description
            binding.tvStarCount.text = repoModel?.starCount
            binding.tvForkCount.text = repoModel?.forksCount


            try {
                Picasso.get().load(repoModel?.ownerModel?.avatarUrl)
                    .into(binding.civProfile, object : Callback {
                        override fun onSuccess() {
                            binding.civProfile.visibility = View.VISIBLE
                        }

                        override fun onError(e: java.lang.Exception?) {
                            binding.civProfile.visibility = View.INVISIBLE
                        }
                    })
            } catch (e: Exception) {
                binding.civProfile.visibility = View.INVISIBLE
            }
        }

    }
}