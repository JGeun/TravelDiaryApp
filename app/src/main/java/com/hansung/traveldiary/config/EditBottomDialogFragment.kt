package com.hansung.traveldiary.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hansung.traveldiary.databinding.DialogBtmEditBinding

class EditBottomDialogFragment(val itemClick : (Int) -> Unit) : BottomSheetDialogFragment(){
    private lateinit var binding : DialogBtmEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflater = LayoutInflater.from(requireContext())
        binding = DialogBtmEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMove.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        binding.tvDelete.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
    }
}