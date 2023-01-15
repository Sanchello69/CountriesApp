package com.vas.countriesapp.presentation.listCountries

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.vas.countriesapp.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import com.vas.countriesapp.utils.RemoteResult

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel by viewModels<ListViewModel>()
    private var binding: FragmentListBinding? = null
    private var listAdapter: ListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(inflater, container, false)

        setupUI()
        setupObservers()

        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        listAdapter = null
        super.onDestroyView()
    }

    private fun setupUI() {
        listAdapter = ListAdapter{
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(it)
            view?.let { view ->  Navigation.findNavController(view).navigate(action) }
        }
        binding?.recyclerView?.adapter = listAdapter

        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.getListCountries()
            binding?.swipeRefresh?.isRefreshing = false
        }
    }

    private fun setupObservers() {
        viewModel.listCountries.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                RemoteResult.Status.SUCCESS -> {
                    binding?.progressBar?.isVisible = false
                    listAdapter?.data = result?.data ?: listOf()
                }
                RemoteResult.Status.LOADING -> binding?.progressBar?.isVisible = true
                RemoteResult.Status.ERROR -> {
                    binding?.apply {
                        progressBar.isVisible = false
                        errorTextView.text = result.message.toString()
                        errorTextView.isVisible = true
                    }
                }
            }
        }
    }

}