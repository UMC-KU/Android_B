package com.example.flo.ui.main.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PanelVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Panel1Fragment()
            1 -> Panel2Fragment()
            2 -> Panel3Fragment()
            else -> Panel4Fragment()
        }
    }
}