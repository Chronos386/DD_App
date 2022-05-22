package com.example.dd_app.fragments.masterBarFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dd_app.R
import com.example.dd_app.adapters.AccountsAdapter
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.*
import com.example.dd_app.databinding.FragmentCharactersBinding
import com.example.dd_app.fragments.dialogFragments.WhatDoWithGamerFragment
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.GoToAcc
import javax.inject.Inject

class GamersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
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
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        binding.massage.text = getString(R.string.no_gamers)
        binding.progressBar.visibility = View.VISIBLE
        binding.SpisRV.visibility = View.INVISIBLE
        binding.massage.visibility = View.INVISIBLE

        val thr = Thread(kotlinx.coroutines.Runnable {
            netHelper.getAccountsByGame(game.id)
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
        val arrAccounts = AccountsData.fromJson(netHelper.str)
        val adapter = AccountsAdapter(requireContext(), arrAccounts, object : GoToAcc {
            override fun onClicked(data: AccountData){
                val dialog = WhatDoWithGamerFragment.newInstance(data, acc, game)
                dialog.show(parentFragmentManager, "customDialog")
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
        fun newInstance(item: AccountData, gameItem: GameData) =
            GamersFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                }
            }
    }
}