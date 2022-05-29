package com.example.flo.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentPanel2Binding
import com.example.flo.databinding.FragmentPanel3Binding
import com.example.flo.databinding.FragmentPanel4Binding

class Panel4Fragment: Fragment() {
    lateinit var binding: FragmentPanel4Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPanel4Binding.inflate(inflater,container,false)
        return binding.root
    }
}