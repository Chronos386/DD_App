package com.example.dd_app.fragments.dialogFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.databinding.DialogFragmentDelAccountBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class DelAccountFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentDelAccountBinding
    @Inject lateinit var dataBase: DataFromDB
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var acc: AccountData
    lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentDelAccountBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        dataBase.initDataBase(requireContext())

        title = binding.title
        val cancel = binding.exitBtn
        val confirm = binding.dellBtn

        cancel.setOnClickListener {
            this.onDestroyView()
        }

        confirm.setOnClickListener {
            val thr = Thread(kotlinx.coroutines.Runnable {
                dataBase.clearAccountTable()
                val str = acc.toJson()
                netHelper.dellAccount(str)
                this.onDestroyView()
                navigator().goToLoginFrag()
            })
            thr.start()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(item: AccountData) =
            DelAccountFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                }
            }
    }
}