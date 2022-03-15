package com.bd.dana.hiltpractice.ui.home

import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bd.dana.hiltpractice.api.models.app_model.AppModel
import com.bd.dana.hiltpractice.databinding.ItemViewEachAppBinding
import com.bumptech.glide.Glide

class AppAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<AppModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewModel(ItemViewEachAppBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewModel){
            val model = dataList[position]
            val binding = holder.binding

            Glide.with(binding.appIcon).load(model.getIcon()).into(binding.appIcon)
            binding.appName.text = model.getName()
            binding.appPackage.text = model.getPackages()

        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewModel(val binding: ItemViewEachAppBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun initLoad(list: List<AppModel>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }
}