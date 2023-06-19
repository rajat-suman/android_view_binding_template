package com.viewBindingTemplate.main.activity.mainActivity

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.viewBindingTemplate.customclasses.datastore.DataStoreUtil
import com.viewBindingTemplate.customclasses.datastore.DatastoreKeys
import com.viewBindingTemplate.sample.ui.dialogs.exit.ExitDialog
import com.viewBindingTemplate.utils.adjustResize
import com.viewbinding.R
import com.viewbinding.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController by lazy { findNavController(R.id.fragmentContainerView) }

    @Inject
    lateinit var dataStore: DataStoreUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        attachBackPress()
        window.adjustResize() // To set the Adjust resize
    }

    private fun attachBackPress() {
        onBackPressedDispatcher.addCallback {
            if (navController.backQueue.size == 2) {
                ExitDialog().show(supportFragmentManager, "TAG")
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        navController.addOnDestinationChangedListener(this)
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?,
    ) = Unit

    override fun onDestroy() {
        super.onDestroy()
        dataStore.removeKey(DatastoreKeys.IS_GUEST_USER) {}
    }

}
