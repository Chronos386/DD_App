package com.example.dd_app.fragments.dialogFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.DialogFragmentWhatDoWithGameBinding
import com.example.dd_app.fragments.contact.navigator

class WhatDoWithGamerFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentWhatDoWithGameBinding
    private lateinit var accMaster: AccountData
    private lateinit var accGamer: AccountData
    private lateinit var game: GameData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accGamer = it.getSerializable(ARG_PARAM1) as AccountData
            accMaster = it.getSerializable(ARG_PARAM2) as AccountData
            game = it.getSerializable(ARG_PARAM3) as GameData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentWhatDoWithGameBinding.inflate(inflater, container,
            false)
        val title = binding.title
        val enterBtn = binding.enterBtn
        val delBtn = binding.delBtn

        title.text = accGamer.login
        enterBtn.text = getString(R.string.see_gamer_char_btn)
        delBtn.text = getString(R.string.expel_game_btn)
        delBtn.setOnClickListener {
            this.onDestroyView()
            val dialog = SureDelGamerFragment.newInstance(accGamer, accMaster, game)
            dialog.show(parentFragmentManager, "customDialog")

        }
        enterBtn.setOnClickListener {
            this.onDestroyView()
            navigator().goToMasterGamerCharactersFrag(accGamer, accMaster, game)
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
        fun newInstance(gamer: AccountData, master: AccountData, gameItem: GameData) =
            WhatDoWithGamerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, gamer)
                    putSerializable(ARG_PARAM2, master)
                    putSerializable(ARG_PARAM3, gameItem)
                }
            }
    }
}