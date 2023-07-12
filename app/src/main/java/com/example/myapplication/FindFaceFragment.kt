package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFindFaceBinding

class FindFaceFragment: Fragment()  {
    private var _binding: FragmentFindFaceBinding? = null
    private val binding get() = _binding!!

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
    }

    //왜 null 이지..
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}