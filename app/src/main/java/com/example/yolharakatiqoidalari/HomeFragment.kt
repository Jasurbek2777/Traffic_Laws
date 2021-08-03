package com.example.yolharakatiqoidalari

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.yolharakatiqoidalari.adapters.ViewPagerAdapter
import com.example.yolharakatiqoidalari.databinding.FragmentHomeBinding
import com.example.yolharakatiqoidalari.databinding.SelectedItemBinding
import com.example.yolharakatiqoidalari.databinding.TabItemBinding
import com.google.android.material.tabs.TabLayout

private const val ARG_PARAM1 = "param1"
lateinit var binding: FragmentHomeBinding
private lateinit var type_list3: ArrayList<String>
lateinit var viewPagerAdapter: ViewPagerAdapter
private lateinit var type1: String
var bottom_nav_position: Int = 0

class HomeFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        param1 = "0"
        binding = FragmentHomeBinding.inflate(inflater, container, false)

      //  setTabs()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        loadCategory()
        type1 = "Ogohlantiruvchi"
        viewPagerAdapter = ViewPagerAdapter(
            type_list3,
            childFragmentManager,
            bottom_nav_position
        )
        binding.viewPager.adapter = viewPagerAdapter
       // binding.tabLayout.setupWithViewPager(binding.viewPager)
        setupView()
        binding.bottomNav.setOnNavigationItemSelectedListener {
           val itemId = it.itemId
            when(itemId){
                R.id.home1->{
                    binding.viewPager.currentItem  =0
                   // findNavController().navigate(R.id.homeFragment)
                }
                R.id.liked1->{
                    binding.viewPager.currentItem  =1
                  //  findNavController().navigate(R.id.leekFragment)
                }
                R.id.info1->{
                    binding.viewPager.currentItem  =2
                   // findNavController().navigate(R.id.infoFragment)
                }
            }
            true
        }
        return binding.root

    }

    private fun setupView() {
        viewPagerAdapter = ViewPagerAdapter(
            type_list3,
            childFragmentManager,
            bottom_nav_position
        )
        binding.viewPager.adapter = viewPagerAdapter

        binding.viewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0->{
                        binding.bottomNav.menu.findItem(R.id.home1).isChecked=true
                    }
                    1->{
                        binding.bottomNav.menu.findItem(R.id.liked1).isChecked=true
                    }
                    2->{
                        binding.bottomNav.menu.findItem(R.id.info1).isChecked=true
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    override fun onResume() {
        super.onResume()
        loadCategory()
//        Toast.makeText(binding.root.context, "Click", Toast.LENGTH_SHORT).show()

    }

    private fun loadCategory() {
        type_list3 = ArrayList()
        type_list3.add("Ogohlantiruvchi")
        type_list3.add("Imtiyozli")
        type_list3.add("Taqiqlovchi")
        type_list3.add("Buyuruvchi")
        type_list3.add("Axborot-ishora")
        type_list3.add("Servis")
        type_list3.add("Qo'shimcha axborot")
    }
}