package com.example.dd_app.fragments.masterBarFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dd_app.R
import com.example.dd_app.adapters.AccountsAdapter
import com.example.dd_app.adapters.CharactersAdapter
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.*
import com.example.dd_app.databinding.FragmentCharactersBinding
import com.example.dd_app.fragments.dialogFragments.WhatDoWithGamerFragment
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.GoToAcc
import com.example.dd_app.help_components.GoToCharacter
import javax.inject.Inject

class GamersCharactersInGameFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var accGamer: AccountData
    private lateinit var accMaster: AccountData
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
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        binding.massage.text = getString(R.string.no_gamers)
        binding.progressBar.visibility = View.VISIBLE
        binding.SpisRV.visibility = View.INVISIBLE
        binding.massage.visibility = View.INVISIBLE

        val thr = Thread(kotlinx.coroutines.Runnable {
            netHelper.getCharactersByAccAndGame(accGamer.id, game.id)
            if(netHelper.str.length != 2)
                requireActivity().runOnUiThread {
                    setCharactersInform()
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.SpisRV.visibility = View.VISIBLE
                }
            else
                requireActivity().runOnUiThread {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.massage.visibility = View.VISIBLE
                }
        })
        thr.start()

        return binding.root
    }

    private fun setCharactersInform() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.SpisRV.layoutManager = linearLayoutManager
        val arrCharacters = CharactersData.fromJson(netHelper.str)
        val adapter = CharactersAdapter(requireContext(), arrCharacters, object : GoToCharacter {
            override fun onClicked(data: CharacterData){
                //Наконец-то идём смотреть чужого перса
                //Идём с акком мастера и с инфой перса игрока (той, что дата)
                println("POP3")
            }
        })
        binding.SpisRV.adapter = adapter
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
            GamersCharactersInGameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, gamer)
                    putSerializable(ARG_PARAM2, master)
                    putSerializable(ARG_PARAM3, gameItem)
                }
            }
    }
}