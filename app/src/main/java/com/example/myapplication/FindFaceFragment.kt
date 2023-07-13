package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFindFaceBinding
import android.Manifest.permission
import android.os.Build

class FindFaceFragment: Fragment()  {
    private var _binding: FragmentFindFaceBinding? = null
    private val binding get() = _binding!!

    //퍼미션 확인 하는 작업을 fragment말고 다른 곳에서 하기
    //그때 무슨 패턴 있었는데.. flow로도 구현할 수 있고..
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindFaceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.result.setOnClickListener {
            findNavController().navigate(
                R.id.action_findFaceFragment_to_findFaceResultFragment
            )
        }

        binding.history.setOnClickListener {
            findNavController().navigate(
                R.id.action_findFaceFragment_to_historyFragment
            )
        }

        checkOrRequirePermission(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) permission.READ_MEDIA_IMAGES
            else permission.READ_EXTERNAL_STORAGE
        ){

        }

        binding.cameraBtn.setOnClickListener {
            checkOrRequirePermission(permission.CAMERA){
                dispatchTakePictureIntent()
            }
        }
    }

    // TODO:: callback flow로 구현하기
    fun checkOrRequirePermission(permission: String, onPermissionGranted: ()->Unit){
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, 0)
            }
        }
    }

    //왜 null 이지..
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}