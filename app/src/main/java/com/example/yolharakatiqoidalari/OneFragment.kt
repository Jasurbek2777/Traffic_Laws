package com.example.yolharakatiqoidalari

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yolharakatiqoidalari.databinding.FragmentOneBinding
import com.example.yolharakatiqoidalari.db.MyDbHelper
import com.example.yolharakatiqoidalari.models.Sign

private const val ARG_PARAM1 = "param1"
lateinit var mydbOne: MyDbHelper

class OneFragment : Fragment() {
    private var param1: String? = null
    lateinit var bindingOne: FragmentOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mydbOne = MyDbHelper(requireContext())
        bindingOne = FragmentOneBinding.inflate(inflater, container, false)
        val sign = mydbOne.getbyId(param1?.toInt()!!)
        val image = sign.sign_image
        bindingOne.toolbarTitle.setText(sign.sign_name)
        val bitmap = image?.size?.let { BitmapFactory.decodeByteArray(image, 0, it) }
        bindingOne.oneImage.setImageBitmap(bitmap)
        bindingOne.oneName.setText(sign.sign_name)
        bindingOne.oneDesc.setText(sign.sign_desc)


        bindingOne.back.setOnClickListener { findNavController().popBackStack() }
        return bindingOne.root
    }


}