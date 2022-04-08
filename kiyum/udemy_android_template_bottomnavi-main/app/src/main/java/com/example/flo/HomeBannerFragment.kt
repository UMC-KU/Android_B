package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBannerBinding

class HomeBannerFragment
    (
    val bgRes: Int,
    val titleRes: String,
    val infoRes: String,
    val albumRes1: Int,
    val albumTitleRes1: String,
    val singerRes1: String,
    val albumRes2: Int,
    val albumTitleRes2: String,
    val singerRes2: String
) : Fragment() {
    lateinit var binding: FragmentHomeBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBannerBinding.inflate(inflater, container, false)

        binding.homePannelBackgroundIv.setImageResource(bgRes)
        binding.homePannelTitleTv.setText(titleRes)
        binding.homePannelBtnPlayIv.setImageResource(R.drawable.btn_panel_play_large)
        binding.homePannelAlbumInfoTv.setText(infoRes)

        binding.homePannelAlbumImgIv.setImageResource(albumRes1)
        binding.homePannelAlbumTitleTv.setText(albumTitleRes1)
        binding.homePannelAlbumSingerTv.setText(singerRes1)

        binding.homePannel2ndAlbumImgIv.setImageResource(albumRes2)
        binding.homePannel2ndAlbumTitleTv.setText(albumTitleRes2)
        binding.homePannel2ndAlbumSingerTv.setText(singerRes2)

        return binding.root
    }
}