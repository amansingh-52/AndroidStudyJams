package com.aman.ottdisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DetailsRVAdapter(private val list: ArrayList<DetailsDataClass>): RecyclerView.Adapter<DetailsRVAdapter.DetailsVH>(){

    class DetailsVH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val imdb : TextView = itemView.findViewById(R.id.imdbTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsVH {
        return DetailsVH(LayoutInflater.from(parent.context).inflate(R.layout.details_card_view,parent,false))
    }

    override fun onBindViewHolder(holder: DetailsVH, position: Int) {
        val currentItem = list[position]
        Picasso.get().load(currentItem.url).into(holder.imageView)
        holder.title.text = currentItem.title
        holder.imdb.text = currentItem.imdbRating
    }

    override fun getItemCount(): Int = list.size


}