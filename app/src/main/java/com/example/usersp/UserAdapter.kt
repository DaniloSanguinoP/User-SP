package com.example.usersp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.usersp.databinding.ItemUserAltBinding
import com.example.usersp.databinding.ItemUserBinding

class UserAdapter(private val users: MutableList<User>, private val listener: OnClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder> () {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemUserAltBinding.bind(view)

        fun setListener(user: User, position: Int) {
            binding.root.setOnClickListener { listener.onCLick(user, position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_user_alt, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        val humanPosition = position + 1

        with(holder) {
            setListener(user, humanPosition)
            binding.tvOrder.text = humanPosition.toString()
            binding.tvName.text = user.getFullName()
            Glide.with(context)
                .load(user.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgPhoto)
        }
    }

    fun remove(position: Int) {
        users.removeAt(position)
        notifyItemRemoved(position)
    }
}