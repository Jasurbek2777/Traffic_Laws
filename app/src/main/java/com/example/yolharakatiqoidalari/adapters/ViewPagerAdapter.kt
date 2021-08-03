package com.example.yolharakatiqoidalari.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.yolharakatiqoidalari.InfoFragment
import com.example.yolharakatiqoidalari.LikeFragment
import com.example.yolharakatiqoidalari.RvFragment

class ViewPagerAdapter(
    var list: List<String>,
    fm: FragmentManager, var nav_position: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                return RvFragment.newInstance(position.toString())
            }
            1->{
                return LikeFragment()
            }
            2->{
                return InfoFragment()
            }
            else->{
                return RvFragment.newInstance(position.toString())
            }
        }

    }
}