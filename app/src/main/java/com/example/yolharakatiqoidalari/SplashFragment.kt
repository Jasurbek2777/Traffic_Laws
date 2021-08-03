package com.example.yolharakatiqoidalari

import android.content.ClipData.newIntent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yolharakatiqoidalari.databinding.FragmentSplashBinding


lateinit var handler: Handler
lateinit var splashBinding: FragmentSplashBinding

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        splashBinding = FragmentSplashBinding.inflate(inflater, container, false)
        handler=Handler()
        handler.postDelayed(Runnable {
            kotlin.run {
                findNavController().popBackStack()
                findNavController().navigate(R.id.homeFragment)

            }
        }, 2000)

        return splashBinding.root
    }


}