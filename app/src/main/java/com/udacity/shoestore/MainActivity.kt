package com.udacity.shoestore

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.features.shoe.ShoeListFragmentDirections

val TOP_LEVEL_DESTINATIONS: Set<Int> =
    setOf(
        R.id.loginFragment,
        R.id.welcomeFragment,
        R.id.instructionFragment,
        R.id.shoeListFragment
    )

val NO_TOOLBAR_DESTINATIONS: Set<Int> =
    setOf(
        R.id.loginFragment,
        R.id.welcomeFragment,
        R.id.instructionFragment,
    )

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)

//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        appBarConfiguration = AppBarConfiguration
            .Builder(TOP_LEVEL_DESTINATIONS)
            .setOpenableLayout(binding.drawerLayout)
            .build()

//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val params: CoordinatorLayout.LayoutParams =
                binding.myNavHostFragment.layoutParams as CoordinatorLayout.LayoutParams
            if (destination.id in NO_TOOLBAR_DESTINATIONS) {//NO_TOOLBAR_DESTINATIONS.contains(destination.id)
                binding.appbar.visibility = View.GONE
                params.behavior = null
            } else {
                binding.appbar.visibility = View.VISIBLE
                params.behavior = AppBarLayout.ScrollingViewBehavior()
            }
            binding.drawerLayout.setDrawerLockMode(
                if (destination.id == R.id.shoeListFragment)
                    DrawerLayout.LOCK_MODE_UNLOCKED
                else
                    DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            )
            binding.detailButton.visibility =
                if (destination.id == R.id.shoeListFragment) View.VISIBLE
                else View.GONE
            binding.myNavHostFragment.requestLayout()
        }

        binding.navView.run {
            setupWithNavController(navController)
            setNavigationItemSelectedListener(this@MainActivity)
        }

        binding.detailButton.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(
                ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment(null)
            )
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        if (item.itemId == R.id.action_logout) {
            viewModel.onLogout()
            findNavController(R.id.myNavHostFragment).navigate(ShoeListFragmentDirections.actionLogout())
            return true
        }
        return item.onNavDestinationSelected(findNavController(R.id.myNavHostFragment))
    }

//    override fun onOptionsItemSelected(item: MenuItem) =
//        item.onNavDestinationSelected(findNavController(R.id.myNavHostFragment))
//                || super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
}
