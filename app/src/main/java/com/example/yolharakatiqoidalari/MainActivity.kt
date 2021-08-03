package com.example.yolharakatiqoidalari

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.yolharakatiqoidalari.databinding.ActivityMainBinding

lateinit var bind_main: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind_main = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(bind_main.root)

    }

    override fun onNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.splashFragment).navigateUp()
    }

}