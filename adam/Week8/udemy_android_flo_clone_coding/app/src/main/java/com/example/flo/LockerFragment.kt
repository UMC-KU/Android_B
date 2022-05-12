package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding

    private val information = arrayListOf("저장한 곡", "음악파일", "저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerDetailVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerTitleTb, binding.lockerDetailVp) {
            tab, position->
            tab.text = information[position]
        }.attach()

        binding.lockerLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

//        initRecyclerView()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun getJwt():Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        //Fragment에서 sharedPreference 사용할때 이렇게 쓰면 된다.
        return spf!!.getInt("jwt",0)
    }

    private fun initViews(){
        val jwt : Int = getJwt()
        if(jwt == 0){
            binding.lockerLoginTv.text = "로그인"
            //jwt에 저장된 값 없으면 로그인해야되는 상황
            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        } else {
            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                //로그아웃 진행
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")
        editor.apply()
    }

//    private fun initRecyclerView() {
//        binding.lockerRecyclerviewRv.layoutManager = LinearLayoutManager(this,
//            LinearLayoutManager.VERTICAL, false)
//    }


}