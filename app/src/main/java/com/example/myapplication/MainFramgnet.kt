package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.delegate.IPermissionDelegation
import com.example.myapplication.delegate.PermissionDelegation
import kotlinx.coroutines.launch

class MainFramgnet : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //권한을 관리하는 delegation
    private val permissionDelegation: IPermissionDelegation by lazy {
        PermissionDelegation(requireActivity().activityResultRegistry)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(permissionDelegation as LifecycleObserver)
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

        binding.historyBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_main_fragment_to_history_fragment
            )
        }

        binding.selectPictureBtn.setOnClickListener {
            lifecycleScope.launch {
                //갤러리 사진을 read하기 위한 권한
                val readFilePermission =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
                    else Manifest.permission.READ_EXTERNAL_STORAGE

                //필요한 권한 요청
                val requestResult = permissionDelegation.checkOrRequestPermission(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        readFilePermission
                    )
                )

                //갤러리 read권한이 granted된 경우 화면이동
                if (requestResult[readFilePermission] == true) {
                    findNavController().navigate(
                        R.id.action_main_fragment_to_select_picture_fragment
                    )
                } else {
                    //권한 재요청
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}