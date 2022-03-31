package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        binding.songLilacLayoutCl.setOnClickListener {
            Toast.makeText(activity, "LILAC", Toast.LENGTH_SHORT).show()
        }
        binding.songFluLayoutCl.setOnClickListener {
            Toast.makeText(activity, "FLU", Toast.LENGTH_SHORT).show()
        }
        binding.songCoinLayoutCl.setOnClickListener {
            Toast.makeText(activity, "COIN", Toast.LENGTH_SHORT).show()
        }
        binding.songMixoffTg.setOnClickListener {
            setSongMix(true)
        }
        binding.songMixonTg.setOnClickListener {
            setSongMix(false)
        }
        return binding.root
    }

    fun setSongMix(isToggleOn: Boolean) {
        if (isToggleOn) {
            binding.songMixoffTg.visibility = View.GONE
            binding.songMixonTg.visibility = View.VISIBLE

        } else {
            binding.songMixoffTg.visibility = View.VISIBLE
            binding.songMixonTg.visibility = View.GONE
        }

    }


}