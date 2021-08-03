package com.example.yolharakatiqoidalari.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yolharakatiqoidalari.InnerFragment
import com.example.yolharakatiqoidalari.models.Category

class ViewPagerAdapter2(var list: List<Category>, fragmentActivity: FragmentActivity, position: Int, ) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return InnerFragment.newInstance(list[position].position!!)
    }

}