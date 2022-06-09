package com.app.aktham.stockmarketapp.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.aktham.stockmarketapp.databinding.CompanyListItemViewBinding
import com.app.aktham.stockmarketapp.domain.model.CompanyListing

class CompanyListingAdapter(
    val companyListingOnItem: CompanyListingOnItem
) :
    ListAdapter<CompanyListing, CompanyListingAdapter.CompanyListViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CompanyListing>() {
            override fun areItemsTheSame(p0: CompanyListing, p1: CompanyListing) = p0 == p1
            override fun areContentsTheSame(p0: CompanyListing, p1: CompanyListing) =
                p0.name == p1.name
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CompanyListViewHolder {
        val binding = CompanyListItemViewBinding.inflate(
            LayoutInflater.from(viewGroup.context)
        )
        return CompanyListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CompanyListViewHolder, position: Int) {
        viewHolder.bind(
            getItem(position)
        )
    }


    inner class CompanyListViewHolder(
        private val binding: CompanyListItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                // on item list click
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    companyListingOnItem.onClick(
                        adapterPosition, getItem(adapterPosition)
                    )
                }
            }
        }

        fun bind(companyListing: CompanyListing) {
            binding.companyName.text = companyListing.name
            binding.companyMarket.text = companyListing.exchange
            binding.companySymbol.text = companyListing.symbol
        }

    }

    interface CompanyListingOnItem {
        fun onClick(position: Int, companyListing: CompanyListing)
    }

}