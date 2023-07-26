package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentImageViewerBinding


class ImageViewerFragment: Fragment(), OnBackStackChangedListener{
    private var _binding: FragmentImageViewerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SelectPictureViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fragmentManager = navHostFragment?.childFragmentManager
        fragmentManager?.addOnBackStackChangedListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.imageViewer.setImageURI(viewModel.selectedImage.value)
        binding.okBtn.setOnClickListener {
            val selectedImagePath = viewModel.selectedImage.value?.path?: return@setOnClickListener
            val action = ImageViewerFragmentDirections.actionImageViewerFragmentToFindFaceResultFragment(selectedImagePath)
            findNavController().navigate(action)
        }
        binding.cancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fragmentManager = navHostFragment?.childFragmentManager
        fragmentManager?.removeOnBackStackChangedListener(this)
    }

    //image viwer fragment 종료 시 setSelectedImage 비움
    override fun onBackStackChanged() {
        viewModel.setSelectedImage(null)
    }
}