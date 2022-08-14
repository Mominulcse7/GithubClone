package com.mominulcse7.githubclone.features.view.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mominulcse7.githubclone.R
import com.mominulcse7.githubclone.features.model.RepositoryModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RepoListAdapter(
    private val list: ArrayList<RepositoryModel>,
    private val fragment: Fragment,
    private val rContext: Activity
) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>(), Filterable {

    private var listFiltered: ArrayList<RepositoryModel> = list
    private var listener = fragment as RepoListAdapterListener
    private var context = rContext

    interface RepoListAdapterListener {
        fun onDetailsView(item: View, cModel: RepositoryModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.raw_repository, parent, false)
        )
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        with(vh)
        {
            val model = listFiltered[position]
            setData(model, vh)

            cvRoot.setOnClickListener {
                listener.onDetailsView(itemView, model, adapterPosition)
            }

            val listSize = itemCount
            vExtraSpace.visibility = View.GONE
            if (listSize > 3 && position == listSize - 1)
                vExtraSpace.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(model: RepositoryModel, vh: ViewHolder) {
        with(vh) {
            tvOwnerName.text = model.ownerModel?.name
            tvOwnerDescription.text = model.ownerModel?.description
            tvRepoName.text = model.fullName
            tvDescription.text = model.description
            tvStarCount.text = model.starCount
            tvForkCount.text = model.forksCount

            try {
                Picasso.get().load(model.ownerModel?.avatarUrl)
                    .into(vh.civProfile, object : Callback {
                        override fun onSuccess() {
                            vh.civProfile.visibility = View.VISIBLE
                        }

                        override fun onError(e: java.lang.Exception?) {
                            vh.civProfile.visibility = View.INVISIBLE
                        }
                    })
            } catch (e: Exception) {
                vh.civProfile.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listFiltered.isNotEmpty()) listFiltered.size else 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val queryString = constraint.toString().lowercase()
                listFiltered = if (queryString.isEmpty())
                    list
                else {
                    val tempList = ArrayList<RepositoryModel>()
                    for (item in list) {
                        if (
                            item.name!!.lowercase().contains(queryString)
                            || item.fullName!!.lowercase().contains(queryString)
                            || item.ownerModel?.name!!.lowercase().contains(queryString)
                            || item.description!!.lowercase().contains(queryString)
                        )
                            tempList.add(item)
                    }
                    tempList
                }
                val results = FilterResults()
                results.values = listFiltered
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listFiltered = results.values as ArrayList<RepositoryModel>
                notifyDataSetChanged()
            }
        }
    }

    fun setItems(listRepo: java.util.ArrayList<RepositoryModel>) {
        listFiltered = listRepo;
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvRoot: CardView = itemView.findViewById(R.id.cvRoot)
        val tvOwnerName: TextView = itemView.findViewById(R.id.tvOwnerName)
        val civProfile: ImageView = itemView.findViewById(R.id.civProfile)
        val tvOwnerDescription: TextView = itemView.findViewById(R.id.tvOwnerDescription)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvRepoName: TextView = itemView.findViewById(R.id.tvRepoName)
        val tvStarCount: TextView = itemView.findViewById(R.id.tvStarCount)
        val tvForkCount: TextView = itemView.findViewById(R.id.tvForkCount)
        val vExtraSpace: View = itemView.findViewById(R.id.vExtraSpace)
    }
}