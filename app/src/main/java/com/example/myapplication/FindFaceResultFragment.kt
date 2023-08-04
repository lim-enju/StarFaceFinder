package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.CelebritysAdapter
import com.example.myapplication.databinding.FragmentFindFaceResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindFaceResultFragment: Fragment() {
    private var _binding: FragmentFindFaceResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FindFaceViewModel by viewModels()
    private lateinit var celebritysAdapter: CelebritysAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindFaceResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView(){
        with(binding.celebrityList){
            celebritysAdapter = CelebritysAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = celebritysAdapter
        }
    }

    private fun initObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                launch {
                    viewModel.findFaceResult
                        .filterNotNull()
                        .collect { result ->
                            //TODO:: 에러처리
                            val faces = result.getOrNull()?: arrayListOf()

                            celebritysAdapter.isSearchingSuccess = true
                            celebritysAdapter.celebrities = faces
                            celebritysAdapter.notifyItemRangeChanged(0, celebritysAdapter.itemCount)
                    }
                }
                launch {
                    viewModel.imageFile
                        .filterNotNull()
                        .collect { imageFile ->
//                            binding.resultImg.setImageURI(imageFile.toUri())
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