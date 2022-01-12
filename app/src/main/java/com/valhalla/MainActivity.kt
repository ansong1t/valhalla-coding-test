package com.valhalla

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.valhalla.common.navigation.MainNavDirections
import com.valhalla.util.observeIn
import com.valhalla.databinding.ActivityMainBinding
import com.valhalla.util.setLightStatusBarIcons
import com.valhalla.util.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val viewModel by viewModels<MainViewModel>()

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        applyViewInsetters()
        initBottomNav()
        handleExtras()
        observeActions()
        viewModel.liveData.observe(this, ::render)
    }

    private fun applyViewInsetters() {
        val binding = requireBinding()
        binding.bottomNavView.applyInsetter {
            type(navigationBars = true) {
                padding(bottom = true)
            }
        }
    }

    private fun observeActions() {
        viewModel.actions.observeIn(this) { actions ->
            actions.forEach { action ->
                when (action) {
                    MainActions.LightStatusBar -> setLightStatusBarIcons(true)

                    MainActions.DarkStatusBar -> setLightStatusBarIcons(false)

                    is MainActions.NavigateTo -> navController.navigate(action.navDirections)

                    is MainActions.Error -> showAlertDialog(
                        title = "Error",
                        body = action.message
                    )

                    else -> {
                    }
                }
            }
        }
    }

    private fun handleExtras() {
        intent?.extras?.let { bundle ->
            val startScreen = bundle.getString(EXTRA_START_SCREEN) ?: return

            require(startScreen.isNotEmpty()) {
                "EXTRA_START_SCREEN not found!"
            }

            when (startScreen) {
                START_MAIN -> {
                    viewModel.navigate(
                        MainNavDirections
                            .actionGlobalToHomeGraph(),
                        R.id.homeFragment
                    )
                }
            }
        }
    }

    private fun initBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment

        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)

        binding!!.bottomNavView.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        viewModel.handleNavigationDestination(destination.id)
    }

    private fun render(mainViewState: MainViewState) {
        val binding = requireBinding()
        binding.state = mainViewState
    }

    private fun requireBinding() = binding!!

    companion object {

        const val EXTRA_START_SCREEN = "EXTRA_START_SCREEN"
        const val START_MAIN = "START_MAIN"

        fun openActivity(context: Context, extras: Bundle) {
            context.startActivity(
                Intent(context, MainActivity::class.java).apply {
                    putExtras(extras)
                }
            )
        }
    }
}
