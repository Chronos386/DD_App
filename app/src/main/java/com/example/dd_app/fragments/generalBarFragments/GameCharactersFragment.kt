package com.example.dd_app.fragments.generalBarFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dd_app.R
import com.example.dd_app.adapters.CharactersAdapter
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.CharacterData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.dataSource.arrays.CharactersData
import com.example.dd_app.databinding.FragmentGameBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.GoToCharacter
import javax.inject.Inject

class GameCharactersFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
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
        binding = FragmentGameBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        binding.massage.text = getString(R.string.no_characters_game)
        binding.progressBar.visibility = View.VISIBLE
        binding.SpisRV.visibility = View.INVISIBLE
        binding.massage.visibility = View.INVISIBLE

        binding.plusButton.setOnClickListener {
            navigator().goToNewCharacterFrag(acc, game, acc.id)
        }

        val thr = Thread(kotlinx.coroutines.Runnable {
            netHelper.getCharactersByAccAndGame(acc.id, game.id)
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
                navigator().goToCharacterInfoFrag(acc, data, game.masterID)
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
            GameCharactersFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                }
            }
    }
}