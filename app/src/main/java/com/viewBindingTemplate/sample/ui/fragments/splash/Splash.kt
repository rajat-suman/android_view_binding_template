package com.viewBindingTemplate.sample.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.TransitionInflater
import com.viewBindingTemplate.utils.navigation.navigateWithAction
import com.viewbinding.databinding.SplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : Fragment() {

    private var _binding: SplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        view?.navigateWithAction(SplashDirections.actionSplashToSplashFinal())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}