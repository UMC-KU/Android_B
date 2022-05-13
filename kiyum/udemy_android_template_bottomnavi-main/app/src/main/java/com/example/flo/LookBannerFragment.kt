package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLookchartBannerBinding

class LookBannerFragment(
    val albumRank1 : Int,
    val albumRes1: Int,
    val albumTitleRes1: String,
    val singerRes1: String,
    //val albumRank2 : Int,
    val albumRes2: Int,
    val albumTitleRes2: String,
    val singerRes2: String,
    //val albumRank3 : Int,
    val albumRes3: Int,
    val albumTitleRes3: String,
    val singerRes3: String,
    //val albumRank4 : Int,
    val albumRes4: Int,
    val albumTitleRes4: String,
    val singerRes4: String,
    //val albumRank5 : Int,
    val albumRes5: Int,
    val albumTitleRes5: String,
    val singerRes5: String,

    val playButton: Int

): Fragment() {
    lateinit var binding: FragmentLookchartBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookchartBannerBinding.inflate(inflater,container,false)

        binding.lookPannelFLOchartRank1Tv.setText(Integer.toString(albumRank1))
        binding.lookPannelFLOchartAlbum1Iv.setImageResource(albumRes1)
        binding.lookPannelFLOchartTitle1Tv.setText(albumTitleRes1)
        binding.lookPannelFLOchartSinger1Tv.setText(singerRes1)
        binding.lookPannelBtnFlochartPlay1Iv.setImageResource(playButton)

        binding.lookPannelFLOchartRank2Tv.setText(Integer.toString(albumRank1+1))
        binding.lookPannelFLOchartAlbum2Iv.setImageResource(albumRes2)
        binding.lookPannelFLOchartTitle2Tv.setText(albumTitleRes2)
        binding.lookPannelFLOchartSinger2Tv.setText(singerRes2)
        binding.lookPannelBtnFlochartPlay2Iv.setImageResource(playButton)

        binding.lookPannelFLOchartRank3Tv.setText(Integer.toString(albumRank1+2))
        binding.lookPannelFLOchartAlbum3Iv.setImageResource(albumRes3)
        binding.lookPannelFLOchartTitle3Tv.setText(albumTitleRes3)
        binding.lookPannelFLOchartSinger3Tv.setText(singerRes3)
        binding.lookPannelBtnFlochartPlay3Iv.setImageResource(playButton)

        binding.lookPannelFLOchartRank4Tv.setText(Integer.toString(albumRank1+3))
        binding.lookPannelFLOchartAlbum4Iv.setImageResource(albumRes4)
        binding.lookPannelFLOchartTitle4Tv.setText(albumTitleRes4)
        binding.lookPannelFLOchartSinger4Tv.setText(singerRes4)
        binding.lookPannelBtnFlochartPlay4Iv.setImageResource(playButton)

        binding.lookPannelFLOchartRank5Tv.setText(Integer.toString(albumRank1+4))
        binding.lookPannelFLOchartAlbum5Iv.setImageResource(albumRes5)
        binding.lookPannelFLOchartTitle5Tv.setText(albumTitleRes5)
        binding.lookPannelFLOchartSinger5Tv.setText(singerRes5)
        binding.lookPannelBtnFlochartPlay5Iv.setImageResource(playButton)

        return binding.root
    }

}