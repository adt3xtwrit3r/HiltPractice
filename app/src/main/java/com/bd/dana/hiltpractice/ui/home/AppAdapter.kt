package com.bd.dana.hiltpractice.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bd.dana.hiltpractice.R
import com.bd.dana.hiltpractice.api.models.app_model.AppModel
import com.bd.dana.hiltpractice.api.models.app_model.AppTable
import com.bd.dana.hiltpractice.databinding.ItemViewEachAppBinding
import com.bumptech.glide.Glide

class AppAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<AppTable> = mutableListOf()
    var invokeApp:((modeL: AppTable)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewModel(ItemViewEachAppBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewModel){
            val model = dataList[position]
            val binding = holder.binding

            Glide.with(binding.appIcon).load(R.drawable.ic_launcher_background).into(binding.appIcon)
            binding.appName.text = model.appName
            binding.appPackage.text = model.packageName

        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewModel(val binding: ItemViewEachAppBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                invokeApp?.invoke(dataList[adapterPosition])
            }
        }
    }

    fun initLoad(list: List<AppTable>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }
}