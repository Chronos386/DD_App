package com.example.dd_app.fragments.generalBarFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dd_app.R
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.FragmentHomeBinding
import com.example.dd_app.fragments.dialogFragments.sureMake.SureDelGameFragment
import javax.inject.Inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.nameGame.text = game.gameName
        binding.exitBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        if (acc.login != game.masterID) {
            binding.dellBtn.text = getString(R.string.leave_game_btn)
        }
        else {
            binding.dellBtn.text = getString(R.string.del_game_btn)
        }
        binding.dellBtn.setOnClickListener {
            val dialog = SureDelGameFragment.newInstance(acc, game)
            dialog.flag = 1
            dialog.show(parentFragmentManager, "customDialogFrag")
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
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                }
            }
    }
}