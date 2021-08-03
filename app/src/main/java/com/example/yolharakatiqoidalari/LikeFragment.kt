package com.example.yolharakatiqoidalari

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yolharakatiqoidalari.databinding.FragmentLeekBinding
import com.example.yolharakatiqoidalari.adapters.RvAdapter
import com.example.yolharakatiqoidalari.db.MyDbHelper
import com.example.yolharakatiqoidalari.models.Sign

class LikeFragment : Fragment() {

    lateinit var fragmentLikeBinding: FragmentLeekBinding
    lateinit var root1: View
    lateinit var myDbHelperInner: MyDbHelper
    lateinit var list1234: ArrayList<Sign>
    lateinit var rvAdapter1: RvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        list1234 = ArrayList()
        myDbHelperInner = MyDbHelper(requireContext())
        fragmentLikeBinding = FragmentLeekBinding.inflate(inflater, container, false)
        root1 = fragmentLikeBinding.root
        for (i in myDbHelperInner.getAll()) {
            if (i.sign_likable == 1)
                list1234.add(i)
        }
        rvAdapter1 = RvAdapter(list1234, object : RvAdapter.setOnItemCLick {
            override fun itemOnCLick(sign: Sign, position: Int) {
                val bundle = Bundle()
                bundle.putString("param1", sign.sign_id.toString())
                findNavController().navigate(R.id.oneFragment, bundle)
            }

            override fun itemOnEditCLick(sign: Sign, position: Int) {
                val bundle = Bundle()
                bundle.putString("param1", sign.sign_id.toString())
                findNavController().navigate(R.id.editFragment, bundle)
            }

            override fun itemOnDeleteCLick(sign: Sign, position: Int) {
                myDbHelperInner.deleteSign(sign)
                list1234.remove(sign)
                rvAdapter.notifyItemRemoved(position)
                rvAdapter.notifyItemRangeRemoved(position, list1234.size)

            }

            override fun itemLikedClick(sign: Sign, position: Int) {
                myDbHelperInner.editSign(sign)
                list1234[position] = sign
                rvAdapter1.notifyItemChanged(position)
                rvAdapter1.notifyItemRangeChanged(position, list1234.size)
            }
        })
        fragmentLikeBinding.rv.adapter = rvAdapter1
        return root1
    }

    override fun onResume() {
        super.onResume()
        fragmentLikeBinding.rv.adapter = rvAdapter1
    }


}