package com.example.dd_app.fragments.dialogFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.CharacterData
import com.example.dd_app.databinding.DialogFragmentWhatDoCharacterBinding

class WhatDoWithCharacterFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentWhatDoCharacterBinding
    private lateinit var acc: AccountData
    private lateinit var character: CharacterData
    private lateinit var masterName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
            character = it.getSerializable(ARG_PARAM2) as CharacterData
            masterName = it.getSerializable(ARG_PARAM3) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentWhatDoCharacterBinding.inflate(inflater, container,
            false)

        binding.spellBtn.setOnClickListener {
            this.onDestroyView()

            println("spell") //Окно заклинаний
        }

        binding.redactBtn.setOnClickListener {
            this.onDestroyView()
            if(acc.login == masterName)
                println("master redact") //окно редактирования мастера
            else
                println("gamer redact") //окно редактирования геймера
        }

        binding.dellBtn.setOnClickListener {
            this.onDestroyView()
            val dialog = SureDelCharacterFragment.newInstance(character)
            dialog.show(parentFragmentManager, "customDialog")
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        private val ARG_PARAM2 = "item2"

        @JvmStatic
        private val ARG_PARAM3 = "item3"

        @JvmStatic
        fun newInstance(account: AccountData, characterItem: CharacterData, masterName: String) =
            WhatDoWithCharacterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, account)
                    putSerializable(ARG_PARAM2, characterItem)
                    putSerializable(ARG_PARAM3, masterName)
                }
            }
    }
}