package com.aman.ottdisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GenreAdapter(private val list: ArrayList<GenreDataClass>,
            private val listener : OnItemClickListener) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(){

    inner class GenreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val textView: TextView = itemView.findViewById(R.id.genreName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(textView.text.toString())
        }

    }

    interface OnItemClickListener{
        fun onItemClick(string: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.genre_card_view,parent,false)
        return GenreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView.text = currentItem.genre
    }

    override fun getItemCount(): Int = list.size

}