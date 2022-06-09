package com.app.aktham.stockmarketapp.presentation.companyDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.app.aktham.stockmarketapp.R
import com.app.aktham.stockmarketapp.databinding.FragmentCompanyDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyDetailsFragment : Fragment(R.layout.fragment_company_details) {

    private var _binding : FragmentCompanyDetailsBinding? = null
    private val binding get() = _binding!!
    private val companyDetailsViewModel: CompanyDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCompanyDetailsBinding.bind(view)

        companyDetailsViewModel.state.observe(viewLifecycleOwner) { companyDetailsState ->
            Log.d("CompanyDetails", companyDetailsState.toString())
            companyDetailsState?.let {
                binding.rootView.isVisible = !it.isLoading
                binding.companyDetailsProgress.isVisible = it.isLoading
                it.errorMessage?.let {
                    binding.errorMsgTv.text = it
                    binding.errorMsgTv.isVisible = true
                    binding.rootView.isVisible = false
                }

                binding.companyNameTv.text = it.companyDetails?.name ?: ""
                binding.companySymbolTv.text = it.companyDetails?.symbol ?: ""
                binding.companyCountryTv.text = "Country: ".plus(it.companyDetails?.country ?: "")
                binding.companyIndustryTv.text = "Industry: ".plus(it.companyDetails?.industry ?: "")
                binding.companyDescriptionTv.text = it.companyDetails?.description ?: ""
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}