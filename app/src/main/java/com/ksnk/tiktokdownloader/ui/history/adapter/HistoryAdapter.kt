package com.ksnk.tiktokdownloader.ui.history.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.ksnk.tiktokdownloader.R
import com.ksnk.tiktokdownloader.databinding.ItemHistoryBinding
import com.ksnk.tiktokdownloader.ui.MediaModel
import com.ksnk.tiktokdownloader.utils.Navigation
import java.io.File
import java.security.AccessController.getContext


class HistoryAdapter(
    private val items: List<MediaModel>,
    private val context: Context,
    private val navigation: Navigation
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        create(parent, context, navigation)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(items[position])


    inner class HistoryViewHolder(
        private val binding: ItemHistoryBinding,
        private val context: Context,
        private val navigation: Navigation
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MediaModel) {
            Glide.with(context).load(item.uri).error(R.drawable.rounded_corners).into(binding.imageViewCover)
            binding.textViewDuration.text = item.duration
            binding.textViewSize.text = item.size
            binding.imageViewCover.setOnClickListener {
                navigation.openPlayerFragmentFromHistory(item.uri.toString(), item.name)
            }
        }
    }

    private fun create(
        parent: ViewGroup,
        context: Context,
        navigation: Navigation
    ): HistoryViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, context, navigation)
    }
}

