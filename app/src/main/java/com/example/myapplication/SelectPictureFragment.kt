package com.example.myapplication

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.adapter.ImageAdapter
import com.example.myapplication.databinding.FragmentFindFaceBinding
import com.example.myapplication.delegate.FileInputDelegation
import com.example.myapplication.delegate.IFileInputDelegation
import com.example.myapplication.delegate.IPermissionDelegation
import com.example.myapplication.delegate.PermissionDelegation
import com.example.myapplication.utils.dpToPx
import com.example.myapplication.utils.parcelable
import com.example.myapplication.view.SpacingItemDecorator
import com.example.myapplication.view.SpacingItemDecorator.SpacingType
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class SelectPictureFragment: Fragment() {
    private var _binding: FragmentFindFaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectPictureViewModel by activityViewModels()

    private lateinit var imageAdapter: ImageAdapter

    var cameraLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val bitmap: Bitmap? = result.data?.parcelable("data")
            bitmap?.let { image ->
                //temp file로 저장 후 uri 변환
                val file = fileInputDelegation.saveTempFile(image)
                viewModel.setSelectedImage(file.toUri())
            }
        }
    }

    //권한을 관리하는 delegation
    private val permissionDelegation: IPermissionDelegation by lazy {
        PermissionDelegation(requireActivity().activityResultRegistry)
    }

    //파일을 관리하는 delegation
    private val fileInputDelegation: IFileInputDelegation by lazy {
        FileInputDelegation(requireContext())
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
        _binding = FragmentFindFaceBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.fetchImageItemList(requireContext())
        initView()
        initObserver()
        return view
    }

    private fun initView(){
        with(binding){
            imageAdapter = ImageAdapter{ uri ->
                viewModel.setSelectedImage(uri)
            }
            imagesRecycler.adapter = imageAdapter
            imagesRecycler.layoutManager = GridLayoutManager(requireContext(), 4)
            imagesRecycler.addItemDecoration(SpacingItemDecorator(1.dpToPx(), SpacingType.TOP, SpacingType.BOTTOM, SpacingType.LEFT, SpacingType.RIGHT))

            cameraBtn.setOnClickListener {
                dispatchTakePictureIntent()
            }
        }
    }

    private fun initObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            /*
             Lifecycle.State.CREATE로 할 경우 ImageViewerFragment에서 back key를 누르면 SelectPictureFragment의 selectedImage flow가 collect 된 후
             ImaveViewerFragment 가 Destory 된다..
             Lifecycle.State.RESUMED 으로 작성하면 Destory 가 된 후 SelectPictureFragment의 selectedImage 가 collect 된다..
            */
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED){
                launch {
                    viewModel.imageList.collect { images ->
                        imageAdapter.images = images
                    }
                }

                launch {
                    viewModel.selectedImage
                        .filterNotNull()
                        .distinctUntilChanged()
                        .collect {
                            findNavController().navigate(
                                R.id.action_select_picture_fragment_to_imageViewerFragment
                            )
                        }
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        lifecycleScope.launch {
            //카메라 권한 체크
            val isGranted = permissionDelegation.checkOrRequestPermission(requireActivity(), CAMERA)
            if(isGranted){
                //카메라 실행
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                        cameraLauncher.launch(takePictureIntent)
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