package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.adapter.ImageAdapter
import com.example.myapplication.databinding.FragmentFindFaceBinding
import com.starFaceFinder.data.common.TAG
import kotlinx.coroutines.launch


class SelectPictureFragment: Fragment()  {
    private var _binding: FragmentFindFaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectPictureViewModel by activityViewModels()

    private lateinit var imageAdapter: ImageAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindFaceBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.fetchImageItemList(requireContext())
        initView()
        initOberser()
        return view
    }

    fun initView(){
        with(binding){
            imageAdapter = ImageAdapter()
            imagesRecycler.adapter = imageAdapter
            imagesRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
    fun initOberser(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.selectedPictureList.collect{ images ->
                    imageAdapter.images = images
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