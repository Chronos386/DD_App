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
import com.example.dd_app.databinding.DialogFragmentJoinGameBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class JoinGameFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentJoinGameBinding
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
        binding = DialogFragmentJoinGameBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)

        val regBtn = binding.regBtn
        val confBtn = binding.confBtn
        val psw = binding.password
        val gameName = binding.gameName

        regBtn.setOnClickListener {
            this.onDestroyView()
            val dialog = CreateNewGameFragment.newInstance(acc)
            dialog.show(parentFragmentManager, "customDialog")
        }

        confBtn.setOnClickListener {
            if(gameName.text.toString() != ""){
                if(psw.text.toString() != "") {
                    if(psw.text.toString().length <= 10) {
                        val thr = Thread(kotlinx.coroutines.Runnable {
                            netHelper.checkGameExist(gameName.text.toString())
                            if(netHelper.str == "Yes") {
                                netHelper.getGameInf(gameName.text.toString(), psw.text.toString())
                                if(netHelper.str.isNotEmpty()) {
                                    requireActivity().runOnUiThread {
                                        this.onDestroyView()
                                    }
                                    val myGame = GameData.fromJson(netHelper.str)
                                    if (myGame != null) {
                                        if(acc.login != myGame.masterID)
                                            navigator().goToGamerGameFrag(acc, myGame)
                                        else
                                            navigator().goToMasterGameFrag(acc, myGame)
                                    }
                                }
                                else {
                                    requireActivity().runOnUiThread {
                                        val toastTXT = "???????????????? ???????????? ????????"
                                        Toast.makeText(requireContext(), toastTXT,
                                            Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                            else {
                                requireActivity().runOnUiThread {
                                    val toastTXT = "???????? ?? ?????????? ???????????? ???? ????????????????????"
                                    Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        })
                        thr.start()
                    }
                    else {
                        val toastTXT = "???????????? ???????????? ???????? ???? ?????????????? 10-???? ????????????????"
                        Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    val toastTXT = "?????????????? ???????????? ????????"
                    Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
                }
            }
            else {
                val toastTXT = "?????????????? ???????????????? ????????"
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(item: AccountData) =
            JoinGameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                }
            }
    }
}