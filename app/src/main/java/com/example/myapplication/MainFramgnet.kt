package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.databinding.FragmentMainBinding

class MainFramgnet: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //퍼미션 확인 하는 작업을 fragment말고 다른 곳에서 하기
    //그때 무슨 패턴 있었는데.. flow로도 구현할 수 있고.. 델리게이트
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        }

    var cameraLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_main_fragment_to_history_fragment
            )
        }

        binding.history.setOnClickListener {
            findNavController().navigate(
                R.id.action_main_fragment_to_history_fragment
            )
        }

        checkOrRequirePermission(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
            else Manifest.permission.READ_EXTERNAL_STORAGE
        ){

        }

        binding.startBtn.setOnClickListener {
            checkOrRequirePermission(Manifest.permission.CAMERA){
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
                cameraLauncher.launch(takePictureIntent)
            }
        }
    }

    //왜 null 이지..
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}