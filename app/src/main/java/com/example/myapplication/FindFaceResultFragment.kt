package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.adapter.CelebritysAdapter
import com.example.myapplication.databinding.FragmentFindFaceResultBinding
import com.starFaceFinder.data.common.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindFaceResultFragment : Fragment() {
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

    private fun initView() {
        with(binding.celebrityList) {
            celebritysAdapter = CelebritysAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = celebritysAdapter
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.searchedSimilarFace
                        .filterNotNull()
                        .collect { result ->
                            if (result.isFailure) {
                                Toast.makeText(
                                    requireContext(),
                                    R.string.network_error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            val faces = result.getOrNull() ?: arrayListOf()
                            celebritysAdapter.isSearchingComplete = true
                            celebritysAdapter.celebrities = faces
                            celebritysAdapter.notifyItemRangeChanged(0, celebritysAdapter.itemCount)
                        }
                }
                launch {
                    viewModel.searchedFaceInfo
                        .combine(viewModel.imageFile) { faceInfoResult, imageFile ->
                            with(binding){
                                val faceInfo = faceInfoResult.getOrNull()

                                if(faceInfoResult.isFailure || faceInfo == null) {
                                    //TODO:: 에러처리
                                    return@combine
                                }

                                if(faceInfo.isEmpty()){
                                    //TODO:: 에러처리
                                    return@combine
                                }

                                if(faceInfo.size != 1){
                                    //TODO:: 에러처리
                                    return@combine
                                }

                                val face = faceInfo.first()

                                Glide
                                    .with(requireContext())
                                    .load(imageFile)
                                    .override(300)
                                    .into(selectedImg)

                                //성별 표시
                                var confidence = face.gender?.confidence?.toFloat()?.times(100)?.toInt()?:0
                                var genderText: String? = null
                                when(face.gender?.value) {
                                    "male" -> {
                                        genderText = "남성"
                                        childGenderView.isVisible = false
                                        maleGenderView.setValue(confidence)
                                        femaleGenderView.setValue(100 - confidence)
                                    }

                                    "female" -> {
                                        genderText = "여성"
                                        childGenderView.isVisible = false
                                        femaleGenderView.setValue(confidence)
                                        maleGenderView.setValue(100 - confidence)
                                    }
                                    "child" -> {
                                        genderText = "어린이"
                                        maleGenderView.isVisible = false
                                        femaleGenderView.isVisible = false
                                        childGenderView.setValue(confidence)
                                    }
                                    else -> {}
                                }

                                //나이 표시
                                confidence = face.age?.confidence?.toFloat()?.times(100)?.toInt()?:0
                                ageTxt.text = "${face.age?.value}세 $genderText ${confidence}%"
                            }
                        }.collect()
                }
            }
        }
    }

    private fun drawRectangle(imageView: ImageView, x: Float, y: Float) {
        val bitmap = (imageView.drawable as? BitmapDrawable)?.bitmap ?: return

        val scaledX = x / imageView.width * bitmap.width
        val scaledY = y / imageView.height * bitmap.height

        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

        val canvas = Canvas(bitmap.copy(Bitmap.Config.ARGB_8888, true))
        canvas.drawRect(
            scaledX - 50, // 왼쪽 위 X 좌표
            scaledY - 50, // 왼쪽 위 Y 좌표
            scaledX + 50, // 오른쪽 아래 X 좌표
            scaledY + 50, // 오른쪽 아래 Y 좌표
            paint
        )

        imageView.setImageBitmap(bitmap)
    }

    //왜 null 이지..
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}