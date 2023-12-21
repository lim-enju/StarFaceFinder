package com.example.myapplication

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.adapter.CelebritiesAdapter
import com.example.myapplication.databinding.FragmentFindFaceResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindFaceResultFragment : Fragment() {
    private var _binding: FragmentFindFaceResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FindFaceViewModel by viewModels()
    private lateinit var celebritiesAdapter: CelebritiesAdapter

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
        showFaceInfoLayoutTravelData(true)

        with(binding.celebrityList) {
            celebritiesAdapter = CelebritiesAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = celebritiesAdapter
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
//                launch {
//                    viewModel.imageFile
//                        .catch {
//                            Toast.makeText(requireContext(), "파일을 찾지 못했습니다.", Toast.LENGTH_SHORT).show()
//                        }
//                        .collect()
//                }
                launch {
                    viewModel.similarFaceInfos
                        .collect { faces ->
                            celebritiesAdapter.isSearchingComplete = true
                            celebritiesAdapter.celebrities = faces
                            celebritiesAdapter.notifyItemRangeChanged(
                                0,
                                celebritiesAdapter.itemCount
                            )
                        }
                }

                launch {
                    viewModel.toastMsg.collect { msg ->
                        Toast.makeText(
                            requireContext(),
                            msg,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                launch {
                    viewModel.searchFaceInfo
                        .filterNotNull()
                        .zip(
                            viewModel.faceImgFile.filterNotNull()
                        ) { faceInfo, faceImgFile ->
                            with(binding.layoutFaceInfo) {
                                showFaceInfoLayoutTravelData(false)

                                Glide
                                    .with(requireContext())
                                    .load(faceImgFile)
                                    .override(300)
                                    .into(selectedImg)

                                //성별 표시
                                var confidence = faceInfo.genderConfidence ?: 0
                                var genderText: String? = null
                                when (faceInfo.gender) {
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
                                confidence = faceInfo.ageConfidence ?: 0
                                ageTxt.text = "${faceInfo.age}세 $genderText ${confidence}%"
                            }
                        }.collect()
                }
            }
        }
    }

    private fun showFaceInfoLayoutTravelData(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerFaceInfoLayout.startShimmer()
            binding.shimmerFaceInfoLayout.visibility = View.VISIBLE
            binding.layoutFaceInfo.root.visibility = View.GONE
        } else {
            binding.shimmerFaceInfoLayout.stopShimmer()
            binding.shimmerFaceInfoLayout.visibility = View.GONE
            binding.layoutFaceInfo.root.visibility = View.VISIBLE
        }
    }

    //더이상 화면에서 사용되지 않으므로 null로 set함
    //가비지 컬렉션에 의해 메모리가 해제되도록 함
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}