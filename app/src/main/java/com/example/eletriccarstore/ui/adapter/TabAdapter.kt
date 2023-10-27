package com.example.eletriccarstore.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eletriccarstore.ui.CarFragment
import com.example.eletriccarstore.ui.FavoritesFragment

class TabAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> return CarFragment()
            1 -> return FavoritesFragment()
            else -> CarFragment()
        }
    }
}