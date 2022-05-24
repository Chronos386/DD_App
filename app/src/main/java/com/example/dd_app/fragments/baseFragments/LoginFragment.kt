package com.example.dd_app.fragments.baseFragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.databinding.FragmentLoginBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject


class LoginFragment : Fragment() {
    @Inject lateinit var netHelper: DataFromNetwork
    @Inject lateinit var dataBase: DataFromDB
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        dataBase.initDataBase(requireContext())
        val username = binding.username
        val password = binding.password
        val login = binding.login
        val registration = binding.registration

        login.setOnClickListener {
            val usr = username.text.toString()
            val psw = password.text.toString()
            if (usr != "")
                if (psw != "") {
                    if(psw.length <= 10) {
                        val thr = Thread(kotlinx.coroutines.Runnable {
                            login(usr, psw)
                        })
                        thr.start()
                    }
                    else {
                        val toastTXT = "Пароль должен быть не длиннее 10-ти символов"
                        Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    val toastTXT = "Введите пароль"
                    Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
                }
            else{
                val toastTXT = "Введите логин"
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
            }
        }

        registration.setOnClickListener {
            val usr = username.text.toString()
            val psw = password.text.toString()
            if (usr != "")
                if (psw != ""){
                    if(psw.length <= 10) {
                        val thr = Thread(kotlinx.coroutines.Runnable {
                            registration(usr, psw)
                        })
                        thr.start()
                    }
                    else {
                        val toastTXT = "Пароль должен быть не длиннее 10-ти символов"
                        Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    val toastTXT = "Введите пароль"
                    Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
                }
            else{
                val toastTXT = "Введите логин"
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun login(usr: String, psw: String) {
        netHelper.checkAccountExist(usr)
        if(netHelper.str == "Yes") {
            netHelper.getAccountInf(usr, psw)
            if(netHelper.str.isNotEmpty()) {
                val acc = AccountData.fromJson(netHelper.str)
                if (acc != null) {
                    dataBase.addNewAccount(acc)
                    navigator().goToMainFrag()
                }
            }
            else{
                val toastTXT = "Пароль неверный"
                requireActivity().runOnUiThread() {
                    Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {
            val toastTXT = "Такого аккаунта не существует"
            requireActivity().runOnUiThread() {
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registration(usr: String, psw: String) {
        netHelper.checkAccountExist(usr)
        if(netHelper.str == "No") {
            val newAcc = AccountData(1, usr, psw, 2)
            val str = newAcc.toJson()
            netHelper.addNewAccount(str)
            while (netHelper.str == "No") {
                netHelper.checkAccountExist(usr)
            }
            netHelper.getAccountInf(usr, psw)
            if(netHelper.str.isNotEmpty()) {
                val acc = AccountData.fromJson(netHelper.str)
                if (acc != null) {
                    dataBase.addNewAccount(acc)
                    navigator().goToMainFrag()
                }
            }
        }
        else {
            val toastTXT = "Такой аккаунт уже существует"
            requireActivity().runOnUiThread() {
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_SHORT).show()
            }
        }
    }
}