package com.example.techtest.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.techtest.data.entities.Profile
import com.example.techtest.databinding.ItemListContentBinding

class ProfileAdapter(private val listener: ProfileItemListener) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    interface ProfileItemListener {
        fun onClickedProfile(characterId: String)
    }

    private val items = ArrayList<Profile>()

    fun setItems(items: ArrayList<Profile>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun add(items: ArrayList<Profile>) {
        val count = this.itemCount
        val newItemsCount = items.size
        this.items.addAll(items)
        notifyItemRangeInserted(count, newItemsCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemListContentBinding =
            ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(items[position])


}

class CharacterViewHolder(
    private val itemBinding: ItemListContentBinding,
    private val listener: ProfileAdapter.ProfileItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var profile: Profile

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Profile) {
        this.profile = item
        itemBinding.name.text = """${item.firstName}  ${item.lastName}"""
        itemBinding.title.text = """${item.title}"""
        Glide.with(itemBinding.root)
            .load(item.picture)
            .transform(CircleCrop())
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickedProfile(profile.id)
    }
}