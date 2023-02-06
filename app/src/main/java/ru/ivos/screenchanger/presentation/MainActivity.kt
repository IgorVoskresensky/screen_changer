package ru.ivos.screenchanger.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivos.screenchanger.R
import ru.ivos.screenchanger.databinding.ActivityMainBinding
import ru.ivos.screenchanger.presentation.fragments.*
import ru.ivos.screenchanger.utils.Constants

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    var fragment = Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            setContentView(binding.root)

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.fragmentContainer) as NavHostFragment

            navController = navHostFragment.navController

            binding.bnvMain.setupWithNavController(
                navController = navController
            )
        }
    }

    override fun onBackPressed() {
        when(fragment) {
            is CategoryListFragment -> {
                findNavController(R.id.fragmentContainer).navigate(R.id.action_categoryListFragment_to_homeFragment)
            }
            is ImageFragment -> {
                findNavController(R.id.fragmentContainer).navigate(R.id.action_imageFragment_to_categoryListFragment)
            }
            is AboutFragment -> {
                findNavController(R.id.fragmentContainer).navigate(R.id.action_aboutFragment_to_homeFragment)
            }
            is FavoritesFragment -> {
                findNavController(R.id.fragmentContainer).navigate(R.id.action_favoritesFragment_to_homeFragment)
            }
            is HomeFragment -> {
                finish()
            }
        }
    }
}