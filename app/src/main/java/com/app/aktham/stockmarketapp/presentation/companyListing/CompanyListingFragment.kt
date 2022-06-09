package com.app.aktham.stockmarketapp.presentation.companyListing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aktham.stockmarketapp.R
import com.app.aktham.stockmarketapp.databinding.FragmentCompanyListingBinding
import com.app.aktham.stockmarketapp.domain.adapter.CompanyListingAdapter
import com.app.aktham.stockmarketapp.domain.model.CompanyListing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyListingFragment : Fragment(R.layout.fragment_company_listing),
    CompanyListingAdapter.CompanyListingOnItem {

    private var _binding: FragmentCompanyListingBinding? = null
    private val binding get() = _binding!!
    private val companyListingViewModel: CompanyListingViewModel by viewModels()

    private lateinit var companyListAdapter: CompanyListingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCompanyListingBinding.bind(view)

        // setup company list recyclerView list
        initCompanyRecyclerList()

        // observe data from viewModel
        companyListingViewModel.state.observe(viewLifecycleOwner) { dataState ->
            dataState?.let {
                binding.companyProgressBar.isVisible = it.isLoading
                binding.companyRecyclerView.isVisible = !it.isLoading
                binding.swipeRefresh.isRefreshing = it.isRefreshing

                it.errorMessage?.let { error ->
                    binding.errorMsgTv.text = error
                    binding.errorMsgTv.isVisible = true
                }

                // set data to list
                companyListAdapter.submitList(it.companies)
            }
        }

        binding.stockSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?) = false
            override fun onQueryTextChange(searchQuery: String?): Boolean {
                searchQuery?.let {
                    companyListingViewModel.onEvent(
                        CompanyListingEvent.OnSearchQueryChange(query = searchQuery ?: "")
                    )
                }
                return true
            }
        })


        // on Swipe
        binding.swipeRefresh.setOnRefreshListener {
            companyListingViewModel.onEvent(
                CompanyListingEvent.Refresh
            )
        }
    }


    private fun initCompanyRecyclerList() {
        companyListAdapter = CompanyListingAdapter(this)
        binding.companyRecyclerView.setHasFixedSize(true)
        binding.companyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.companyRecyclerView.adapter = companyListAdapter
    }

    // company list item onClick listener
    override fun onClick(position: Int, companyListing: CompanyListing) {
        // go to company details fragment
        val action =
            CompanyListingFragmentDirections.actionCompanyListingFragmentToCompanyDetailsFragment(
                symbol = companyListing.symbol
            )
        findNavController().navigate(
            action
        )
        // clear search view
        binding.stockSearchView.apply {
            clearFocus()
            setQuery("", false)
            if (!isIconified)
                isIconified = true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}