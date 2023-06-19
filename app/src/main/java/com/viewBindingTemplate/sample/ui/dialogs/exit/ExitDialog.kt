package com.viewBindingTemplate.sample.ui.dialogs.exit

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.viewBindingTemplate.customclasses.singleClickListener.setOnSingleClickListener
import com.viewbinding.databinding.ExitDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExitDialog : DialogFragment() {

    private var _binding: ExitDialogBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.apply {
            setLayout(width, height)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ExitDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandler()
    }

    private fun clickHandler() {
        binding.btNo.setOnSingleClickListener {
            dismiss()
        }
        binding.btYes.setOnSingleClickListener {
            requireActivity().finishAffinity()
        }
    }

}