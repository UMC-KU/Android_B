package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeVPAdapter(fragment:Fragment) :FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3



    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}