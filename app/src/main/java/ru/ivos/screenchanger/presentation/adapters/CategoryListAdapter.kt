package ru.ivos.screenchanger.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.ivos.screenchanger.data.models.Hit
import ru.ivos.screenchanger.databinding.ItemCategoryListBinding

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.CategoryListVieHolder>() {

    private lateinit var clickListener: ItemClickListener

    inner class CategoryListVieHolder(
        private val binding: ItemCategoryListBinding,
        listener: ItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(hit: Hit) {
            binding.apply {
                if (hit.previewWidth > hit.previewHeight) {
//                    Glide.with(itemView).load(hit.largeImageURL).centerInside().into(ivItemCategoryList)
                    Picasso.get().load(hit.previewURL).resize(850, 550).centerCrop()
                        .into(ivItemCategoryList)
                } else {
//                    Glide.with(itemView).load(hit.largeImageURL).centerInside().into(ivItemCategoryList)
                    Picasso.get().load(hit.previewURL).resize(550, 850).centerCrop()
                        .into(ivItemCategoryList)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListVieHolder {
        return CategoryListVieHolder(
            ItemCategoryListBinding.inflate(LayoutInflater.from(parent.context)), clickListener
        )
    }

    override fun onBindViewHolder(holder: CategoryListVieHolder, position: Int) {
        val hit = differ.currentList[position]
        holder.bind(hit)
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