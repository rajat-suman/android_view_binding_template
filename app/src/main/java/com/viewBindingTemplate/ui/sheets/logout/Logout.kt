package com.viewBindingTemplate.ui.sheets.logout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.viewBindingTemplate.R
import com.viewBindingTemplate.customclasses.singleClickListener.setOnSingleClickListener
import com.viewBindingTemplate.databinding.LogoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Logout : BottomSheetDialogFragment(R.layout.logout) {

    private var _binding: LogoutBinding? = null
    private val binding get() = _binding!!
    val viewModel by viewModels<LogoutVM>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LogoutBinding.bind(view)
        initClicks()
    }

    private fun initClicks() {
        binding.btLogoutYes.setOnSingleClickListener {
            dismiss()

        }
        binding.btLogoutNo.setOnSingleClickListener {
            dismiss()
        }
    }


    override fun onResume() {
        super.onResume()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}