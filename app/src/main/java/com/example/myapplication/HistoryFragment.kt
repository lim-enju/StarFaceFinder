package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.HistoryAdapter
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.starFaceFinder.data.common.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var historyAdapter: HistoryAdapter

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
            historyAdapter = HistoryAdapter { fid ->
                val action =
                    HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailFragment(fid)
                findNavController().navigate(action)
            }
            historyList.adapter = historyAdapter
            historyList.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.getHistories().collect { historiesMap ->
                        val histories = historyAdapter.histories + historiesMap.map { entry ->
                            Pair(entry.key, entry.value)
                        }
                        historyAdapter.histories = ArrayList(histories)
                        historyAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    //왜 null 이지..
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}