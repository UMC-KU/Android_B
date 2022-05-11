package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerStoreBinding

class LockerStoreFragment : Fragment() {

    lateinit var binding: FragmentLockerStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerStoreBinding.inflate(inflater, container, false)
        return binding.root
    }
}