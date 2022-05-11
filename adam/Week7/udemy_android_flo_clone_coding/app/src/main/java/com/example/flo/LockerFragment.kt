package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding

    private val information = arrayListOf("저장한 곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerDetailVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerTitleTb, binding.lockerDetailVp) {
            tab, position->
            tab.text = information[position]
        }.attach()

//        initRecyclerView()

        return binding.root
    }

//    private fun initRecyclerView() {
//        binding.lockerRecyclerviewRv.layoutManager = LinearLayoutManager(this,
//            LinearLayoutManager.VERTICAL, false)
//    }


}