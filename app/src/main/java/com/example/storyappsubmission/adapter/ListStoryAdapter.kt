package com.example.storyappsubmission.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyappsubmission.data.responses.ListStoryItem
import com.example.storyappsubmission.databinding.StoryItemBinding
import com.example.storyappsubmission.view.detailstory.DetailStoryActivity

class ListStoryAdapter(private val listStory: List<ListStoryItem>) : RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(StoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    class ListViewHolder(binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var imgPhoto: ImageView = binding.image
        private var tvName: TextView = binding.name
        private var tvDescription: TextView = binding.description
        private var tvTimeStamp: TextView = binding.timestamp

        fun bind(storyItem: ListStoryItem) {

            Glide.with(itemView.context)
                .load(storyItem.photoUrl)
                .into(imgPhoto)
            tvName.text = storyItem.name
            tvDescription.text = storyItem.description
            tvTimeStamp.text = storyItem.createdAt

            itemView.setOnClickListener {

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(imgPhoto, "profile"),
                        Pair(tvName, "name"),
                        Pair(tvDescription, "description"),
                        Pair(tvTimeStamp, "timestamp")
                    )

                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra("Story", storyItem)
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }
}