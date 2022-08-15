package com.mominulcse7.githubclone.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mominulcse7.githubclone.R
import com.mominulcse7.githubclone.databinding.RawRepositoryBinding
import com.mominulcse7.githubclone.model.RepositoryModel
import com.mominulcse7.githubclone.utils.getSqlToDDMMYYHHSS
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RepoListAdapter(private val onDetailsClicked: (RepositoryModel) -> Unit) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {
    private var listFiltered = ArrayList<RepositoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RawRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        with(vh.binding)
        {
            val model = listFiltered[position]

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

    fun setList(rList: ArrayList<RepositoryModel>) {
        listFiltered = rList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (listFiltered.isNotEmpty()) listFiltered.size else 0
    }

    inner class ViewHolder(val binding: RawRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)

}