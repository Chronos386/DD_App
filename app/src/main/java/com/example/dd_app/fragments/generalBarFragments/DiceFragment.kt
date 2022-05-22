package com.example.dd_app.fragments.generalBarFragments

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.dd_app.R
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.FragmentDiceBinding
import javax.inject.Inject
import kotlin.random.Random


class DiceFragment : Fragment() {
    private lateinit var binding: FragmentDiceBinding
    @Inject lateinit var netHelper: DataFromNetwork
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
        binding = FragmentDiceBinding.inflate(inflater, container, false)

        val spinDice = binding.spinnerDice
        val spinCountDice = binding.spinnerCountDice

        ArrayAdapter.createFromResource(requireContext(), R.array.dice_array, R.layout.spin_item)
            .also { adapter -> adapter.setDropDownViewResource(android.R
            .layout.simple_spinner_dropdown_item)
            spinDice.adapter = adapter
        }
        ArrayAdapter.createFromResource(requireContext(), R.array.count_dice_array, R.layout
            .spin_item).also { adapter -> adapter.setDropDownViewResource(android.R
            .layout.simple_spinner_dropdown_item)
            spinCountDice.adapter = adapter
        }

        binding.gameBtn.setOnClickListener {
            val diceFaces = spinDice.getSelectedItem().toString().replace("D", "")
                .toInt()
            val countDice = spinCountDice.getSelectedItem().toString().toInt()
            var polsNumb = 0
            for (i in 1..countDice) {
                polsNumb += (1..diceFaces).random()
            }
            binding.diceNumber.text = polsNumb.toString()
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
            DiceFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                }
            }
    }
}