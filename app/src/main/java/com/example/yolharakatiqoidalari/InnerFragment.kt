package com.example.yolharakatiqoidalari

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yolharakatiqoidalari.adapters.RvAdapter
import com.example.yolharakatiqoidalari.databinding.FragmentInnerBinding
import com.example.yolharakatiqoidalari.db.MyDbHelper
import com.example.yolharakatiqoidalari.models.Sign

private const val ARG_PARAM1 = "param1"
lateinit var myDbHelperInner: MyDbHelper
lateinit var fragmentInnerBinding1: FragmentInnerBinding
lateinit var list123: ArrayList<Sign>
lateinit var rvAdapter: RvAdapter

class InnerFragment : Fragment() {
    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }


    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        list123 = ArrayList()

        fragmentInnerBinding1 = FragmentInnerBinding.inflate(inflater, container, false)
        root = fragmentInnerBinding1.root
        val type_list22 = ArrayList<String>()
        type_list22.add("Ogohlantiruvchi")
        type_list22.add("Imtiyozli")
        type_list22.add("Taqiqlovchi")
        type_list22.add("Buyuruvchi")
        type_list22.add("Axborot-ishora")
        type_list22.add("Servis")
        type_list22.add("Qo'shimcha axborot")
        myDbHelperInner = MyDbHelper(requireContext())
        for (i in myDbHelperInner.getAll()) {
            if (i.sign_type == type_list22[param1!!])
                list123.add(i)
        }
        rvAdapter = RvAdapter(list123, object : RvAdapter.setOnItemCLick {
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
                list123.remove(sign)
                rvAdapter.notifyItemRemoved(position)
                rvAdapter.notifyItemRangeRemoved(position, list123.size)

            }

            override fun itemLikedClick(sign: Sign, position: Int) {
                list123[position] = sign
                myDbHelperInner.editSign(sign)
                rvAdapter.notifyItemChanged(position)
                rvAdapter.notifyItemRangeChanged(position, list123.size)
            }

        })
        fragmentInnerBinding1.rv.adapter = rvAdapter
        return root
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            InnerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}