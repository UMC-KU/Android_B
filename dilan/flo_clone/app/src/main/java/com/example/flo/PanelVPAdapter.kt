package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PanelVPAdapter(fragment:Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->Panel1Fragment()
            else->Panel2Fragment()
        }
    }
}