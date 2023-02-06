package ru.ivos.screenchanger.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.ivos.screenchanger.data.models.Favorite
import ru.ivos.screenchanger.databinding.ItemCategoryListBinding

class FavoritesAdapter() : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>(){

    private lateinit var clickListener: ItemClickListener

    inner class FavoritesViewHolder(private val binding: ItemCategoryListBinding, listener: ItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(favorite: Favorite) {
            binding.apply {
//                Log.d(Constants.LOG_TAG, hit.largeImageURL)
//                Log.d(Constants.LOG_TAG, "adapter")
//                Picasso.get().load(hit.previewURL).resize(650, 550).centerCrop().into(ivItemCategoryList)
                Picasso.get().load(favorite.previewURL).resize(650, 650).centerCrop().into(ivItemCategoryList)
//                if(favorite.previewWidth > hit.previewHeight) {
//                    Glide.with(itemView).load(hit.largeImageURL).centerInside().into(ivItemCategoryList)
//                    Picasso.get().load(hit.previewURL).resize(850, 550).centerCrop().into(ivItemCategoryList)
//                } else {
//                    Glide.with(itemView).load(hit.largeImageURL).centerInside().into(ivItemCategoryList)
//                    Picasso.get().load(hit.previewURL).resize(550, 850).centerCrop().into(ivItemCategoryList)
//                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemCategoryListBinding.inflate(LayoutInflater.from(parent.context)), clickListener
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favorite = differ.currentList[position]
        holder.bind(favorite)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        clickListener = listener
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }
}