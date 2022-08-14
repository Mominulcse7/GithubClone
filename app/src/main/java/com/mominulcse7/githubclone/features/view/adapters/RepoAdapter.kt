package com.mominulcse7.githubclone.features.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mominulcse7.githubclone.databinding.RawRepositoryBinding
import com.mominulcse7.githubclone.features.model.RepositoryModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RepoAdapter(private val onDetailsClicked: (RepositoryModel) -> Unit) :
    ListAdapter<RepositoryModel, RepoAdapter.ViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RawRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it, position)
        }
    }

    inner class ViewHolder(private val binding: RawRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RepositoryModel, position: Int) {
            with(binding) {
                tvOwnerName.text = model.ownerModel?.name
                tvOwnerDescription.text = model.ownerModel?.description
                tvRepoName.text = model.fullName
                tvDescription.text = model.description
                tvStarCount.text = model.starCount
                tvForkCount.text = model.forksCount

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

                val listSize = itemCount
                vExtraSpace.visibility = View.GONE
                if (listSize > 3 && position == listSize - 1)
                    vExtraSpace.visibility = View.VISIBLE

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