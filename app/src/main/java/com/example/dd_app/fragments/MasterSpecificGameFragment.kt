package com.example.dd_app.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dd_app.R
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.FragmentMasterSpecificGameBinding
import com.example.dd_app.fragments.contact.navigator
import com.google.android.material.bottomnavigation.BottomNavigationView

class MasterSpecificGameFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener
{
    private lateinit var binding: FragmentMasterSpecificGameBinding
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
        binding = FragmentMasterSpecificGameBinding.inflate(inflater, container,
            false)
        binding.navView.setOnNavigationItemSelectedListener(this)
        navigator().setMasterDiceFragment(acc, game)
        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_dice_master -> navigator().setMasterDiceFragment(acc, game)
            R.id.navigation_characters_master -> navigator().setMasterGameCharactersFragment(acc,
                game)
            R.id.navigation_gamers_master -> navigator().setMasterGamersFragment(acc, game)
            R.id.navigation_home_master -> navigator().setMasterHomeFragment(acc, game)
        }
        return true
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        private val ARG_PARAM2 = "item2"

        @JvmStatic
        fun newInstance(item: AccountData, gameItem: GameData) =
            MasterSpecificGameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                }
            }
    }
}