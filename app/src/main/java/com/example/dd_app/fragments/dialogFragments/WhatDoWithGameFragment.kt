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

class WhatDoWithGameFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentWhatDoWithGameBinding
    private lateinit var acc: AccountData
    private lateinit var game: GameData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
            game = it.getSerializable(ARG_PARAM2) as GameData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentWhatDoWithGameBinding.inflate(inflater, container,
            false)

        val title = binding.title
        val enterBtn = binding.enterBtn
        val delBtn = binding.delBtn

        title.text = game.gameName
        if(acc.login != game.masterID) {
            delBtn.text = getString(R.string.leave_game_btn)
        }
        else {
            delBtn.text = getString(R.string.del_game_btn)
        }
        delBtn.setOnClickListener {
            this.onDestroyView()
            val dialog = SureDelGameFragment.newInstance(acc, game)
            dialog.show(parentFragmentManager, "customDialog")
        }
        enterBtn.setOnClickListener {
            this.onDestroyView()
            if(acc.login != game.masterID)
                navigator().goToGamerGameFrag(acc, game)
            else
                navigator().goToMasterGameFrag(acc, game)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        private val ARG_PARAM2 = "item2"

        @JvmStatic
        fun newInstance(item: AccountData, gameItem: GameData) =
            WhatDoWithGameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                }
            }
    }
}