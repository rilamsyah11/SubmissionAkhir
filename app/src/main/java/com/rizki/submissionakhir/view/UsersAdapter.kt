package com.rizki.submissionakhir.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizki.submissionakhir.databinding.ItemRowUserBinding
import com.rizki.submissionakhir.response.GithubUser

class UsersAdapter (private val  listUserGithub : ArrayList<GithubUser>) : RecyclerView.Adapter<UsersAdapter.UserListViewHolder>()  {
    private lateinit var onItemClickCallback : OnItemClickCallback

    class UserListViewHolder(private var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : GithubUser){
            binding.tvUsernameGit.text = user.Username
            Glide.with(itemView.context)
                .load(user.Avatar)
                .into(binding.imgUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(listUserGithub[position])

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUserGithub[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listUserGithub.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(listUser : GithubUser)
    }

}