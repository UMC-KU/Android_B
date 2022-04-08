package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentLookBinding

class LookFragment : Fragment() {

    lateinit var binding: FragmentLookBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookBinding.inflate(inflater, container, false)

        val lookBannerAdapter = BannerVPAdapter(this)

        lookBannerAdapter.addFragment(LookBannerFragment(
            1,
            R.drawable.img_album_exp, "봄여름가을겨울", "BIGBANG (빅뱅)",
            R.drawable.img_album_exp2, "TOMBOY", "(여자)아이들",
            R.drawable.img_album_exp3, "Feel My Rhythm", "Red Velvet (레드벨벳)",
            R.drawable.img_album_exp4, "사랑인가 봐", "멜로망스",
            R.drawable.img_album_exp5, "GANADARA", "박재범",
            R.drawable.btn_miniplayer_play
        ))

        lookBannerAdapter.addFragment(LookBannerFragment(
            6,
            R.drawable.img_album_exp5, "여름여름갈겨울", "BIGBANG (빅뱅)",
            R.drawable.img_album_exp3, "TOMBOY", "(여자)아이들",
            R.drawable.img_album_exp2, "Feel My Rhythm", "Red Velvet (레드벨벳)",
            R.drawable.img_album_exp, "사랑인가 봐", "멜로망스",
            R.drawable.img_album_exp4, "MABASADA", "박재범",
            R.drawable.btn_miniplayer_play
        ))


        binding.lookPannelFlochartVp.adapter = lookBannerAdapter
        binding.lookPannelFlochartVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}