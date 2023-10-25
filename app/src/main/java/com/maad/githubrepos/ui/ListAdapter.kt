package com.maad.githubrepos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.R
import com.maad.githubrepos.databinding.ReposListItemBinding

class ListAdapter(
    private val repos: List<GitHubModel>,
    private val onItemClickListener: OnDateClickListener
) : Adapter<ListAdapter.NoteViewHolder>() {

    fun interface OnDateClickListener {
        fun onShowDateClick(position: Int)
    }

    class NoteViewHolder(val binding: ReposListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ReposListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = NoteViewHolder(binding)
        holder.binding.creationDateTv.setOnClickListener {
            onItemClickListener.onShowDateClick(holder.adapterPosition)
        }

        return holder
    }

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.ownerNameTv.text = repos[position].owner.ownerName
        holder.binding.repoNameTv.text = repos[position].repoName

        Glide
            .with(holder.binding.ownerIv.context)
            .load(repos[position].owner.avatarUrl)
            .error(R.drawable.person_off)
            .into(holder.binding.ownerIv)

    }

}