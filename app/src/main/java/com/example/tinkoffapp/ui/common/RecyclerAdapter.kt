package com.example.tinkoffapp.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tinkoffapp.data.Model
import com.example.tinkoffapp.databinding.RecyclerItemBinding
import com.example.tinkoffapp.extensions.dpToPxInt
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var itemsList = mutableListOf<Model>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
        )
    }

    fun addToLst(list: List<Model>) {
        itemsList.plusAssign(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        itemsList.clear()
    }

    override fun getItemCount() = itemsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvInfo: TextView = itemView.tv_info
        private val ivAvatar: ImageView = itemView.iv_avatar

        fun bind(model: Model) {
            tvInfo.text = model.description
            Glide.with(ivAvatar)
                .asGif()
                .load(model.gifURL)
                .apply(
                    RequestOptions().transform(
                        CenterCrop(),
                        RoundedCorners(ivAvatar.context.dpToPxInt(6))
                    )
                )
                .into(ivAvatar)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(itemsList[position])
}