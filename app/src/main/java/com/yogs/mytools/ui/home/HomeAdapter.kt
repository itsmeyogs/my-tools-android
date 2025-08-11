package com.yogs.mytools.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yogs.mytools.R
import com.yogs.mytools.data.model.Tool

class HomeAdapter(private val listTool: List<Tool>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class HomeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById<ImageView>(R.id.iv_icon)
        val title: TextView = itemView.findViewById<TextView>(R.id.tv_title)
        val desc: TextView = itemView.findViewById<TextView>(R.id.tv_desc)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tool, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = listTool.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val tool = listTool[position]
        holder.image.setImageResource(tool.image)
        holder.title.text = tool.title
        holder.desc.text = tool.desc

        holder.itemView.setOnClickListener {
            val holderPosition = holder.bindingAdapterPosition

            if(holderPosition != RecyclerView.NO_POSITION){
                onItemClickCallback.onItemClicked(listTool[holderPosition])
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: Tool)
    }
}