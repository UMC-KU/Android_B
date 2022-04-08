package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding

    private val information = arrayListOf("수록곡","상세정보","영상")
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        binding.albumPannelBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()
        }
//        binding.albumPannelSurokgokLayout.setOnClickListener {
//            Toast.makeText(activity,"LILAC",Toast.LENGTH_SHORT).show()
//        }
//
//        binding.albumPannelMymixToggleIv.setOnClickListener {
//            setMyMixStatus(false)
//        }
//        binding.albumPannelMymixToggleOffIv.setOnClickListener {
//            setMyMixStatus(true)
//        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumPannelContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumPannelContentTl,binding.albumPannelContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

//    private fun setMyMixStatus(MyMix: Boolean) {
//        if(MyMix){
//            binding.albumPannelMymixToggleOffIv.visibility = View.GONE
//            binding.albumPannelMymixToggleIv.visibility = View.VISIBLE
//        }
//        else{
//            binding.albumPannelMymixToggleOffIv.visibility = View.VISIBLE
//            binding.albumPannelMymixToggleIv.visibility = View.GONE
//        }
//    }
}
