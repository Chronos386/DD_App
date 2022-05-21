package com.example.dd_app.fragments.dialogFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.DialogFragmentMakeNewGameBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class CreateNewGameFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentMakeNewGameBinding
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var acc: AccountData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentMakeNewGameBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)

        val nameGame = binding.gameName
        val nameWorld = binding.worldName
        val psw = binding.password
        val confBtn = binding.confBtn

        confBtn.setOnClickListener {
            if(nameGame.text.toString().isEmpty() or nameWorld.text.toString().isEmpty() or
                psw.text.toString().isEmpty()) {
                val toastTXT = "Заполните все поля"
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                if (psw.text.toString().length <= 10) {
                    val thr = Thread(kotlinx.coroutines.Runnable {
                        netHelper.checkGameExist(nameGame.text.toString())
                        if (netHelper.str == "No") {
                            val newGame = GameData(
                                nameGame.text.toString(), 1, acc.id.toString(),
                                psw.text.toString(), nameWorld.text.toString()
                            )
                            netHelper.addNewGame(newGame.toJson())
                            while (netHelper.str != "Yes") {
                                netHelper.checkGameExist(newGame.gameName)
                            }
                            this.onDestroyView()
                            navigator().setGamesFragment(acc)
                        } else {
                            requireActivity().runOnUiThread {
                                val toastTXT = "Игра с таким именем уже существует"
                                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })
                    thr.start()
                }
                else {
                    val toastTXT = "Паароль не должен быть длиннее 10-ти символов"
                    Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(item: AccountData) =
            CreateNewGameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                }
            }
    }
}