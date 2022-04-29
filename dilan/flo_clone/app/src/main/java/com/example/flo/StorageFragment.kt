package com.example.flo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentStorageBinding

class StorageFragment : Fragment() {

    private lateinit var binding: FragmentStorageBinding
    private var storageData = ArrayList<MyStorageData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageBinding.inflate(inflater, container, false)

        storageData.apply {
            add(MyStorageData("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(MyStorageData("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(MyStorageData("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(MyStorageData("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(MyStorageData("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(MyStorageData("Weekend", "태연 (Tae yeon)", R.drawable.img_album_exp6))
            add(MyStorageData("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(MyStorageData("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(MyStorageData("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(MyStorageData("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(MyStorageData("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(MyStorageData("Weekend", "태연 (Tae yeon)", R.drawable.img_album_exp6))
        }

        val storageRVAdapter = StorageRVAdapter(storageData)
        binding.storageListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.storageListRv.adapter = storageRVAdapter







        return binding.root
    }
}