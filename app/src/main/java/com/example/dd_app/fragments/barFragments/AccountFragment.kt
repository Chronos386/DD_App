package com.example.dd_app.fragments.barFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.databinding.FragmentAccountBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.fragments.dialogFragments.DelAccountDialogFragment
import com.example.dd_app.fragments.dialogFragments.UpdAccountPswFragment
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class AccountFragment : Fragment() {
    @Inject lateinit var dataBase: DataFromDB
    private lateinit var acc: AccountData
    private lateinit var binding: FragmentAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        dataBase.initDataBase(requireContext())
        binding.nameAccount.text = acc.login

        val dell = binding.dellBtn
        val exitAcc = binding.exitBtn
        val updPsw = binding.updatePswBtn

        dell.setOnClickListener {
            val dialog = DelAccountDialogFragment.newInstance(acc)
            dialog.show(parentFragmentManager, "customDialog")
        }

        exitAcc.setOnClickListener {
            val thr = Thread(kotlinx.coroutines.Runnable {
                dataBase.clearAccountTable()
            })
            thr.start()
            navigator().goToLoginFrag()
        }

        updPsw.setOnClickListener {
            val dialog = UpdAccountPswFragment.newInstance(acc)
            dialog.show(parentFragmentManager, "customDialog")
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(item: AccountData) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                }
            }
    }
}