package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.HistoryAdapter
import com.example.myapplication.adapter.SearchedHistoryAdapter
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.starFaceFinder.data.common.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var searchedHistoryAdapter: SearchedHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        with(binding) {
            vm = viewModel
            historyAdapter = HistoryAdapter(
                onClickHistory = { fid ->
                    val action =
                        HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailFragment(fid)
                    findNavController().navigate(action)
                },
                onClickFavorite = { fid, isFavorite ->
                    viewModel.updateFavorite(fid, isFavorite)
                }
            )
            historyList.adapter = historyAdapter
            historyList.layoutManager = LinearLayoutManager(requireContext())
            historyList.itemAnimator = null

            historyList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                        viewModel.loadHistory()
                    }
                }
            })

            searchedHistoryAdapter = SearchedHistoryAdapter(
                onClickHistory = { fid ->
                    val action =
                        HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailFragment(fid)
                    findNavController().navigate(action)
                }
            )
            searchedHistoryList.adapter = searchedHistoryAdapter
            searchedHistoryList.layoutManager = LinearLayoutManager(requireContext())
            searchedHistoryList.itemAnimator = null
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.historyUiState.filterNotNull().collect { uiState ->
                        historyAdapter.histories = uiState.historyItems
                        historyAdapter.notifyItemRangeChanged(
                            0,
                            historyAdapter.histories.size
                        )
                    }
                }
                launch {
                    viewModel.updatedHistoryItem.collect { updatedIndex ->
                        historyAdapter.notifyItemChanged(updatedIndex)
                    }
                }
                launch {
                    viewModel.toastMsg.collectLatest { msg ->
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                }
                launch {
                    viewModel.searchedHistoriesFlow.collectLatest {
                        searchedHistoryAdapter.submitData(it)
                    }
                }
                launch {
                    viewModel.searchedText.collectLatest { query ->
                        binding.historyList.isVisible = query.isNullOrBlank()
                        binding.searchedHistoryList.isVisible = !query.isNullOrBlank()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}