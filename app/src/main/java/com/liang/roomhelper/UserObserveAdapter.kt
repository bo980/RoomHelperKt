package com.liang.roomhelper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liang.roomhelper.databinding.LayoutItemBinding

class UserObserveAdapter : PagedListAdapter<UserEntity, UserObserveAdapter.UserViewHolder> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<LayoutItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_item,
            parent,
            false
        )
        return UserViewHolder(viewDataBinding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val viewDataBinding = DataBindingUtil.getBinding<LayoutItemBinding>(holder.itemView)
        viewDataBinding?.user = getItem(position)
        viewDataBinding?.executePendingBindings()
    }

    constructor() : super(object : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(user: UserEntity, user1: UserEntity): Boolean {
            return user.uid == user1.uid
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(user: UserEntity, user1: UserEntity): Boolean {
            return user.age == user1.age
        }
    })


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}