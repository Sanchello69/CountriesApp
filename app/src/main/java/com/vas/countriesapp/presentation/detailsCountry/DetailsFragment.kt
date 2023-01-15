package com.vas.countriesapp.presentation.detailsCountry

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import coil.load
import com.vas.countriesapp.R
import com.vas.countriesapp.databinding.FragmentDetailsBinding
import com.vas.countriesapp.databinding.FragmentListBinding
import com.vas.countriesapp.presentation.listCountries.ListViewModel
import com.vas.countriesapp.utils.RemoteResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel by viewModels<DetailsViewModel>()
    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        setupObservers()

        return binding?.root
    }

    private fun setupObservers() {
        viewModel.detailsCountry.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                RemoteResult.Status.SUCCESS -> {
                    binding?.apply {
                        progressBar.isVisible = false
                        flagCountry.load(result.data?.flagsUrl)
                        nameTextView.append(result.data?.name)
                        capitalTextView.append(result.data?.capital)
                        regionTextView.append(result.data?.region)
                        timezonesTextView.append(result.data?.timezones?.joinToString())
                        currenciesTextView.append(result.data?.currencies?.joinToString())

                    }
                }
                RemoteResult.Status.LOADING -> binding?.progressBar?.isVisible = true
                RemoteResult.Status.ERROR -> {
                    binding?.progressBar?.isVisible = false
                    Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}