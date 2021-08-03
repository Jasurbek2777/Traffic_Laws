package com.example.yolharakatiqoidalari

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.yolharakatiqoidalari.adapters.ViewPagerAdapter2
import com.example.yolharakatiqoidalari.databinding.FragmentRvragmentBinding
import com.example.yolharakatiqoidalari.databinding.TabItemBinding
import com.example.yolharakatiqoidalari.db.MyDbHelper
import com.example.yolharakatiqoidalari.models.Category
import com.example.yolharakatiqoidalari.models.Sign
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val ARG_PARAM1 = "param1"
lateinit var bindingRv: FragmentRvragmentBinding
var list12 = ArrayList<Sign>()
private lateinit var type1: String
var position: Int = 0
lateinit var myDbHelper: MyDbHelper

class RvFragment : Fragment() {
    private var param1: String? = null

    var type_list11 = ArrayList<Category>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    lateinit var viewPagerAdapter2: ViewPagerAdapter2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        type1 = "Ogohlantiruvchi"
        list12 = ArrayList()
        bindingRv = FragmentRvragmentBinding.inflate(inflater, container, false)
        myDbHelper = MyDbHelper(requireContext())
        setHasOptionsMenu(true)
        type_list11 = ArrayList()
        type_list11.add(Category("Ogohlantiruvchi",0))
        type_list11.add(Category("Imtiyozli",1))
        type_list11.add(Category("Taqiqlovchi",2))
        type_list11.add(Category("Buyuruvchi",3))
        type_list11.add(Category("Axborot-ishora",4))
        type_list11.add(Category("Servis",5))
        type_list11.add(Category("Qo'shimcha axborot",6))
        viewPagerAdapter2 = ViewPagerAdapter2(type_list11,  requireActivity(), position)
        bindingRv.viewPager1.adapter = viewPagerAdapter2

        TabLayoutMediator(bindingRv.tabLayout, bindingRv.viewPager1) { tab, position ->
            tab.text = type_list11[position].name
        }.attach()
        setTabs()



        bindingRv.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab!!.customView
                val bind = TabItemBinding.bind(customView!!)
                position = tab.position
                bind.linear.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_item_round2)
                bind.itemTv.setTextColor(Color.parseColor("#005CA1"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView1 = tab!!.customView
                val bind1 = TabItemBinding.bind(customView1!!)
                bind1.linear.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_item_round)
                bind1.itemTv.setTextColor(Color.parseColor("#FFFFFF"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        return bindingRv.root
    }

    private fun setTabs() {

        for (i in 0 until bindingRv.tabLayout.tabCount) {

            val tab_item: TabItemBinding =
                TabItemBinding.inflate(LayoutInflater.from(requireContext()))
            tab_item.itemTv.text = type_list11[i].name
            bindingRv.tabLayout.getTabAt(i)?.customView = tab_item.root
            if (i != 0) {
                tab_item.linear.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_item_round)
                tab_item.itemTv.setTextColor(Color.parseColor("#FFFFFF"))

            } else {
                tab_item.linear.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_item_round2)

                tab_item.itemTv.setTextColor(Color.parseColor("#005CA1"))
            }


        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            RvFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add, menu)
    }

    override fun onResume() {
        super.onResume()
        viewPagerAdapter2 = ViewPagerAdapter2(type_list11,  requireActivity(), position)
        bindingRv.viewPager1.adapter = viewPagerAdapter2

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                findNavController().navigate(R.id.addSignFragment)
            }
        }
        return true
    }
}