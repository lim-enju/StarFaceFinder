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
import com.example.myapplication.databinding.FragmentHistoryDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryDetailFragment : Fragment() {
    private var _binding: FragmentHistoryDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryDetailViewModel by viewModels()
    
    private lateinit var celebritiesAdapter: CelebritiesAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHistoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView(){
        with(binding){
            celebritiesAdapter = CelebritiesAdapter()
            celebrityList.layoutManager = LinearLayoutManager(requireContext())
            celebrityList.adapter = celebritiesAdapter
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.historyDetail.catch {
                        Toast.makeText(requireContext(), "히스토리 조회 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }.collect { history ->
                        val faceInfo = history.keys.first()
                        val celebrity = history[faceInfo]?: listOf()

                        //유명인 리스트 표시
                        celebritiesAdapter.celebrities = celebrity
                        celebritiesAdapter.isSearchingComplete = true
                        binding.shimmerCelebritiesLayout.isVisible = false

                        //검색한 얼굴 정보를 화면에 표시
                        with(binding.layoutFaceInfo){
                            Glide
                                .with(requireContext())
                                .load(faceInfo.fileUri)
                                .override(300)
                                .into(selectedImg)

                            //성별 표시
                            var confidence = faceInfo.genderConfidence?:0
                            var genderText: String? = null
                            when(faceInfo.gender) {
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
                            confidence = faceInfo.ageConfidence?:0
                            ageTxt.text = "${faceInfo.age}세 $genderText ${confidence}%"
                        }
                    }
                }
            }
        }
    }
}