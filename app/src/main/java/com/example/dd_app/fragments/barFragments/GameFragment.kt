package com.example.dd_app.fragments.barFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dd_app.adapters.GamesAdapter
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.*
import com.example.dd_app.dataSource.arrays.GamesData
import com.example.dd_app.databinding.FragmentGameBinding
import com.example.dd_app.fragments.dialogFragments.JoinGameFragment
import com.example.dd_app.fragments.dialogFragments.whatDoWith.WhatDoWithGameFragment
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.GoToGame
import javax.inject.Inject

class GameFragment : Fragment() {
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var acc: AccountData
    private lateinit var binding: FragmentGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        binding.progressBar.visibility = View.VISIBLE
        binding.SpisRV.visibility = View.INVISIBLE
        binding.massage.visibility = View.INVISIBLE
        val thr = Thread(kotlinx.coroutines.Runnable {
            netHelper.getGamesByAccount(acc.id)
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
        binding.plusButton.setOnClickListener {
            val dialog = JoinGameFragment.newInstance(acc)
            dialog.show(parentFragmentManager, "customDialog")
        }
        return binding.root
    }

    private fun setCharactersInform() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.SpisRV.layoutManager = linearLayoutManager
        val arrGames = GamesData.fromJson(netHelper.str)
        val adapter = GamesAdapter(requireContext(), arrGames, object : GoToGame {
            override fun onClicked(data: GameData){
                val dialog = WhatDoWithGameFragment.newInstance(acc, data)
                dialog.show(parentFragmentManager, "customDialog")
            }
        })
        binding.SpisRV.adapter = adapter
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(item: AccountData) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                }
            }
    }
}