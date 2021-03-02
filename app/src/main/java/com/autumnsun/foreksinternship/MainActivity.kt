package com.autumnsun.foreksinternship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.autumnsun.foreksinternship.databinding.ActivityMainBinding
import com.autumnsun.foreksinternship.fragments.*
import com.autumnsun.foreksinternship.model.MoneyVariable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_ForeksInternship)
        setContentView(binding.root)
        val homeFragment = HomeFragment(this)
        val favoriteFragment = FavoriteFragment()
        makeCurrentFragment(homeFragment)
        binding.bubbleNavigationLayout.setNavigationChangeListener { view, position ->
            when (position) {
                0 -> makeCurrentFragment(homeFragment)
                1 -> makeCurrentFragment(favoriteFragment)
            }
        }
    }


    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_main, fragment)
                .commit()
        }
    }
}