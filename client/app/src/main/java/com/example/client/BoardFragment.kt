package com.example.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.client.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    private lateinit var viewBinding: FragmentBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBoardBinding.inflate(layoutInflater)
        return viewBinding.root
    }
}