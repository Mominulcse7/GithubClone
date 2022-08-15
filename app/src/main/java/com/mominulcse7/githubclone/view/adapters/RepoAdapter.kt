package com.mominulcse7.githubclone.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mominulcse7.githubclone.R
import com.mominulcse7.githubclone.databinding.RawRepositoryBinding
import com.mominulcse7.githubclone.model.RepositoryModel
import com.mominulcse7.githubclone.utils.getSqlToDDMMYYHHSS
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RepoAdapter(private val onDetailsClicked: (RepositoryModel) -> Unit) :
    ListAdapter<RepositoryModel, RepoAdapter.ViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RawRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: RawRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RepositoryModel) {
            with(binding) {
                tvOwnerName.text = model.ownerModel?.name
                tvOwnerDescription.text = model.ownerModel?.description
                tvRepoName.text = model.full_name
                tvDescription.text = model.description
                tvStarCount.text = model.stargazers_count.toString()
                tvForkCount.text = model.forks_count.toString()
                tvLastUpdate.text = tvForkCount.context.getString(
                    R.string.updated_on,
                    getSqlToDDMMYYHHSS(model.updated_at)
                )

                try {
                    Picasso.get().load(model.ownerModel?.avatarUrl)
                        .into(civProfile, object : Callback {
                            override fun onSuccess() {
                                civProfile.visibility = View.VISIBLE
                            }

                            override fun onError(e: java.lang.Exception?) {
                                civProfile.visibility = View.INVISIBLE
                            }
                        })
                } catch (e: Exception) {
                    civProfile.visibility = View.INVISIBLE
                }

                cvRoot.setOnClickListener {
                    onDetailsClicked(model)
                }
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<RepositoryModel>() {
        override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: RepositoryModel,
            newItem: RepositoryModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}