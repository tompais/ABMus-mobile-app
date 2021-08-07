package com.project.app.base

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.project.app.R
import com.project.app.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    var logged:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.fragmentContainer)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        graph.startDestination = R.id.loginFragment
        navController.graph = graph

        //Set status bar style
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = ContextCompat.getColor(baseContext, R.color.white)
    }

    //Use this to manage back
    override fun onBackPressed() {
        var handled = false
        val currentFragment = getCurrentFragment()
        if (currentFragment is BaseFragment<*>) {
            handled = currentFragment.onBackPressed()
        }

        if (!handled) {
            super.onBackPressed()
        }
    }

    private fun getCurrentFragment(): BaseFragment<*>? {
        val navHostFragment = supportFragmentManager.fragments.first() as? NavHostFragment
        var fragmentReturn:BaseFragment<*>? = null
        if(navHostFragment != null) {
            fragmentReturn = navHostFragment.childFragmentManager.fragments.first() as BaseFragment<*>
        }
        return fragmentReturn
    }
}