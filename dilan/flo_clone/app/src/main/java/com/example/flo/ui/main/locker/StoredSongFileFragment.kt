package com.example.flo.ui.main.locker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flo.databinding.FragmentStoredSongFileBinding


class StoredSongFileFragment : Fragment() {
    private lateinit var binding: FragmentStoredSongFileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoredSongFileBinding.inflate(inflater, container, false)
        return binding.root
    }

}