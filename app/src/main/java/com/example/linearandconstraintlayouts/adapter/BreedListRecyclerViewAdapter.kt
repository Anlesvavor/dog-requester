package com.example.linearandconstraintlayouts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.linearandconstraintlayouts.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BreedListRecyclerViewAdapter(
    private var data: List<BreedItem>,
    var onClickListener: (breedItem: BreedItem) -> Unit
) :
    RecyclerView.Adapter<BreedListRecyclerViewAdapter.ItemViewHolder>()
{
    inner class ItemViewHolder(
        view: View
    ) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener
    {
        val breedNameTextView: TextView = view.findViewById(R.id.text_view_breed_name)
        val breedPhoto: ImageView = view.findViewById(R.id.image_view_breed)

        init {
            view.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            onClickListener(data[adapterPosition])
        }
    }

    fun updateData(newData: List<BreedItem>) {
        this.data = newData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.breed_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]

        holder.breedNameTextView.text = item.name
        item.imageUrl?.let {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.ic__11482)
                .resize(120, 120)
                .into(holder.breedPhoto)
        }
    }

    override fun getItemCount(): Int = data.size

    data class BreedItem(
        val name: String,
        val id: Long,
        val imageUrl: String?
    )
}