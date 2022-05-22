package com.example.dd_app.fragments.dialogFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.DialogFragmentDelAccountBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class SureDelGameFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentDelAccountBinding
    @Inject lateinit var dataBase: DataFromDB
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var acc: AccountData
    private lateinit var game: GameData
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
            game = it.getSerializable(ARG_PARAM2) as GameData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentDelAccountBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        dataBase.initDataBase(requireContext())

        val title = binding.title
        val cancel = binding.exitBtn
        val confirm = binding.dellBtn

        cancel.setOnClickListener {
            this.onDestroyView()
        }

        if(acc.login != game.masterID) {
            title.text = getString(R.string.sure_leave_game_btn)
            confirm.setOnClickListener {
                this.onDestroyView()
                if (flag == 1)
                    requireActivity().onBackPressed()
                val thr = Thread(kotlinx.coroutines.Runnable {
                    netHelper.updGameByDelCh(game.id, acc.id)
                    if (flag != 1)
                        navigator().setGamesFragment(acc)
                })
                thr.start()
            }
        }
        else {
            title.text = getString(R.string.sure_del_game_btn)
            confirm.setOnClickListener {
                this.onDestroyView()
                if (flag == 1)
                    requireActivity().onBackPressed()
                val thr = Thread(kotlinx.coroutines.Runnable {
                    netHelper.dellGame(game.toJson())
                })
                thr.start()
                Thread.sleep(1000)
                if (flag != 1)
                    navigator().setGamesFragment(acc)
            }
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
            SureDelGameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                }
            }
    }
}