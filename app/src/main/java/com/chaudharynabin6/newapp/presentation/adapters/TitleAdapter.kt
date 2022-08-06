package com.chaudharynabin6.newapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharynabin6.newapp.databinding.RvSavedTitleItemBinding
import com.chaudharynabin6.newapp.domain.entity.TitleEntity
import javax.inject.Inject

class TitleAdapter @Inject constructor() : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>(){
    inner class TitleViewHolder(val binding : RvSavedTitleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
       val binding = RvSavedTitleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val titleEntity = titleList[position]
        holder.binding.apply {
            rvSavedTitleTitle.text = titleEntity.title
        }
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    private val diffCallback = object  : DiffUtil.ItemCallback<TitleEntity>(){
        override fun areItemsTheSame(oldItem: TitleEntity, newItem: TitleEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TitleEntity, newItem: TitleEntity): Boolean {
           return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    var  titleList : List<TitleEntity>
        get() = differ.currentList
        set(value) = differ.submitList(value)


}