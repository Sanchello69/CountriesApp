package com.vas.countriesapp.presentation.listCountries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vas.countriesapp.R
import com.vas.countriesapp.data.model.CountryModelData
import com.vas.countriesapp.databinding.CountryItemBinding

class ListAdapter(private val onClick: (name: String) -> Unit): RecyclerView.Adapter<ItemViewHolder>(){

    var data: List<CountryModelData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClick)
    }

    override fun getItemCount() = data.size

}

class ItemViewHolder(private val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: CountryModelData, onClick: (name: String) -> Unit) {
        binding.nameTextView.text = item.name
        binding.flagCountry.load(item.flagsUrl)
        binding.itemLayout.setOnClickListener {
            onClick(item.name)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.country_item, parent, false)
            return ItemViewHolder(CountryItemBinding.bind(view))
        }
    }
}