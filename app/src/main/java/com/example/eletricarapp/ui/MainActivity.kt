package com.example.eletricarapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.eletricarapp.Adapter.TabAdapter
import com.example.eletricarapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var tabLoyout: TabLayout
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupView()
        setupTabs()

    }
fun setupView(){
    tabLoyout = binding.tabLayout
    viewPager = binding.vpViewPager


}

    fun setupTabs(){
        val tabAdapter = TabAdapter(this)
        viewPager.adapter = tabAdapter

        // Listener para mudar de tab ao clicar no icone
        tabLoyout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })

        //Sobreescrita para quando for selecionado a tab ele garantir com a posição a tab correta
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLoyout.getTabAt(position)?.select()
            }


        })

    }


}