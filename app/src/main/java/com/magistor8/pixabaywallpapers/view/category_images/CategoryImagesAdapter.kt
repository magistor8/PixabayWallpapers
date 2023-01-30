package com.magistor8.pixabaywallpapers.view.category_images

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magistor8.pixabaywallpapers.data.retrofit.entires.Image
import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData
import com.magistor8.pixabaywallpapers.databinding.ImagesViewholderLeftBinding
import com.magistor8.pixabaywallpapers.databinding.ImagesViewholderLineBinding
import com.magistor8.pixabaywallpapers.databinding.ImagesViewholderRightBinding


class CategoryImagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val RIGHT = 1
    private val LEFT = 2
    private val CENTER = 3
    private val viewTypes: MutableList<Int> = mutableListOf()

    private lateinit var itemClickListener: OnListItemClickListener

    private lateinit var data : ImagesData
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when(viewType) {
            RIGHT -> ImagesViewholderRight(ImagesViewholderRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            LEFT -> ImagesViewholderLeft(ImagesViewholderLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            CENTER -> ImagesViewholderLine(ImagesViewholderLineBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> ImagesViewholderLine(ImagesViewholderLineBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImagesViewholderRight -> holder.bind(data.images.subList(position * 7, (position + 1) * 7))
            is ImagesViewholderLeft -> holder.bind(data.images.subList(position * 7, (position + 1) * 7))
            is ImagesViewholderLine -> holder.bind(data.images.subList(position * 7, data.images.size - 1))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypes[position]
    }

    override fun getItemCount(): Int {
        return (data.images.size / 7) + 1
    }

    fun submitList(nData: ImagesData) {
        if (!this::data.isInitialized) {
            data = nData
            this.notifyDataSetChanged()
            viewHolderTypes()
        } else {
            val currentSize = data.images.size
            data.images += nData.images
            this.notifyItemRangeInserted(currentSize, data.images.size)
            viewHolderTypes()
        }
    }

    private fun viewHolderTypes() {
        for (i in 0 until itemCount) {
            if (i % 2 == 0) viewTypes.add(i, 1)
            if (i % 2 == 1) viewTypes.add(i, 2)
            if (i == itemCount - 1) viewTypes.add(i, 3)
        }
    }

    fun setItemClickListener(itemClickListener: OnListItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    private fun imageClick(image: Image) {
        itemClickListener.onItemClick(image)
    }

    interface OnListItemClickListener {
        fun onItemClick(image: Image)
    }


    inner class ImagesViewholderRight(private val binding: ImagesViewholderRightBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: List<Image>) {
            if(layoutPosition != RecyclerView.NO_POSITION) {
                for (i in data.indices) {
                    Glide.with(binding.root).load(data[i].webformatURL).into(binding.root.getChildAt(i) as AppCompatImageView)
                    binding.root.getChildAt(i).setOnClickListener { imageClick(data[i]) }
                }
            }
        }
    }

    inner class ImagesViewholderLeft(private val binding: ImagesViewholderLeftBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: List<Image>) {
            if(layoutPosition != RecyclerView.NO_POSITION) {
                for (i in data.indices) {
                    Glide.with(binding.root).load(data[i].webformatURL).into(binding.root.getChildAt(i) as AppCompatImageView)
                    binding.root.getChildAt(i).setOnClickListener { imageClick(data[i]) }
                }
            }
        }
    }

    inner class ImagesViewholderLine(private val binding: ImagesViewholderLineBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: List<Image>) {
            if(layoutPosition != RecyclerView.NO_POSITION) {
                for (i in data.indices) {
                    Glide.with(binding.root).load(data[i].webformatURL).into(binding.root.getChildAt(i) as AppCompatImageView)
                    binding.root.getChildAt(i).setOnClickListener { imageClick(data[i]) }
                }
            }
        }
    }

}