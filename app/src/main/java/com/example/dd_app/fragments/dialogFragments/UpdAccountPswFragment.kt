package com.example.dd_app.fragments.dialogFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.databinding.DialogFragmentUpdPswAccountBinding
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class UpdAccountPswFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentUpdPswAccountBinding
    @Inject lateinit var dataBase: DataFromDB
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
        binding = DialogFragmentUpdPswAccountBinding.inflate(inflater, container,
            false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        dataBase.initDataBase(requireContext())

        val oldPassw = binding.oldPassword
        val newPassw = binding.newPassword
        val confirm = binding.confBtn

        confirm.setOnClickListener {
            if(oldPassw.text.toString() == acc.password) {
                if(newPassw.text.toString().length <= 10) {
                    acc.password = newPassw.text.toString()
                    val thr = Thread(kotlinx.coroutines.Runnable {
                        dataBase.clearAccountTable()
                        dataBase.addNewAccount(acc)
                        netHelper.updAccountPsw(acc.toJson())
                    })
                    thr.start()
                    this.onDestroyView()
                }
                else {
                    val toastTXT = "Пароль должен быть не длиннее 10-ти символов"
                    Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_LONG).show()
                }
            }
            else {
                val toastTXT = "Старый пароль неверен"
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
            UpdAccountPswFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                }
            }
    }
}