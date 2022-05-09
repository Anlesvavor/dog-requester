package com.example.linearandconstraintlayouts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.linearandconstraintlayouts.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DogListRecyclerViewAdapter(
    private var data: List<DogItem>,
    val onDismissClickListener: (dogItem: DogItem) -> Unit,
    val onDetailsClickListener: (dogItem: DogItem) -> Unit
) :
    RecyclerView.Adapter<DogListRecyclerViewAdapter.ItemViewHolder>()
{
    inner class ItemViewHolder(
        view: View
    ) :
        RecyclerView.ViewHolder(view)
    {

        val dogPhotoImageView: ImageView = view.findViewById(R.id.image_view_dog_photo)
        val dogNameTextView: TextView = view.findViewById(R.id.text_view_dog_name)
        val dogBreedNameTextView: TextView = view.findViewById(R.id.text_view_dog_breed)
        val detailsButton: Button = view.findViewById(R.id.button_dog_details)
        val dismissButton: Button = view.findViewById(R.id.button_dog_dismiss)
    }

    fun updateData(newData: List<DogItem>) {
        this.data = newData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.dog_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]

        item.imageUrl?. let {
            Picasso
                .get()
                .load(it)
                .resize(120, 120)
                .into(holder.dogPhotoImageView)
        }

        holder.dogNameTextView.text = item.name
        holder.dogBreedNameTextView.text = item.breedName
        holder.detailsButton.setOnClickListener { onDetailsClickListener(item) }
        holder.dismissButton.setOnClickListener { onDismissClickListener(item) }
    }

    override fun getItemCount(): Int = data.size

    data class DogItem(
        val name: String,
        val id: Long,
        val breedName: String,
        val imageUrl: String?
    )
}