package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homePannelFavorableAlbumIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AlbumFragment())
                .commitAllowingStateLoss()
        }

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homePannelViewpager1.adapter = bannerAdapter
        binding.homePannelViewpager1.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val homeBannerAdapter = BannerVPAdapter(this)

        homeBannerAdapter.addFragment(HomeBannerFragment
            (R.drawable.img_first_album_default,
            "매혹적인 음색의 여성보컬", "총 36곡 2022.03.31",
        R.drawable.img_album_exp, "크크루삥뽕", "기염동",
            R.drawable.img_album_exp2, "Celebrity", "커염동"
        ))
        homeBannerAdapter.addFragment(HomeBannerFragment
            (R.drawable.img_first_album_default,
            "활활 타오르는 행복회로", "총 251곡 2022.03.31",
            R.drawable.img_album_exp3, "이게 왜", "엘지",
            R.drawable.img_album_exp4, "안돼지", "구램"
        ))
        homeBannerAdapter.addFragment(HomeBannerFragment
            (R.drawable.img_first_album_default,
            "좋아할만한 아티스트 MIX", "총 31곡 2022.04.07",
            R.drawable.img_album_exp5, "드라마", "아이유 (IU)",
            R.drawable.img_album_exp6, "STAY (Explicit Ver.)", "The Kid Laroi"))

        binding.homePannelVp.adapter = homeBannerAdapter
        binding.homePannelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}