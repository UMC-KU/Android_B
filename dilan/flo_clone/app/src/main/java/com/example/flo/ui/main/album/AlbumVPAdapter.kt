package com.example.flo.ui.main.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.ui.main.song.SongFragment

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


    // 수록곡, 상세정보, 영상 부분을 VP로 구성 -> 3개의 fragment이므로 그냥 3으로 설정.
    override fun getItemCount(): Int = 3

    // banner만들때는 이미지만 바꾸어서 Fragment를 제공했지만 여기서는 각각 다르기 때문에
    // 각각 다른 Fragment를 만들어서 제공.
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }

}