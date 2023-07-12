package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentHistoryBinding

class HistoryFragment: Fragment()  {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    //왜 null 이지..
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}