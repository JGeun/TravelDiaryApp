package com.hansung.traveldiary.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hansung.traveldiary.databinding.DialogBtmDeleteBinding

class DeleteBottomDialogFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding : DialogBtmDeleteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflater = LayoutInflater.from(requireContext())
        binding = DialogBtmDeleteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvModify.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        binding.tvDelete.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
    }
}