package com.example.dd_app.fragments.dialogFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.DialogFragmentDelAccountBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class SureDelGamerFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentDelAccountBinding
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var acc: AccountData
    private lateinit var master: AccountData
    private lateinit var game: GameData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
            master = it.getSerializable(ARG_PARAM3) as AccountData
            game = it.getSerializable(ARG_PARAM2) as GameData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentDelAccountBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)

        val title = binding.title
        val cancel = binding.exitBtn
        val confirm = binding.dellBtn

        title.text = getString(R.string.sure_expel_gamer_btn)
        cancel.setOnClickListener {
            this.onDestroyView()
        }

        confirm.setOnClickListener {
            this.onDestroyView()
            val thr = Thread(kotlinx.coroutines.Runnable {
                netHelper.updGameByDelCh(game.id, acc.id)
                Thread.sleep(1000)
                navigator().setMasterGamersFragment(master, game)
            })
            thr.start()
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
        fun newInstance(item: AccountData, master: AccountData, gameItem: GameData) =
            SureDelGamerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                    putSerializable(ARG_PARAM3, master)
                }
            }
    }
}