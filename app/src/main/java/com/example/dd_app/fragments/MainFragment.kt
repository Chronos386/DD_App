package com.example.dd_app.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.dd_app.R
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.databinding.FragmentMainBinding
import com.example.dd_app.fragments.contact.navigator
import com.example.dd_app.help_components.DaggerAppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {
    @Inject lateinit var netHelper: DataFromNetwork
    @Inject lateinit var dataBase: DataFromDB
    private lateinit var binding: FragmentMainBinding
    private lateinit var dataAcc: AccountData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        dataBase.initDataBase(requireContext())
        val thr = Thread(kotlinx.coroutines.Runnable {
            val arrAcc = dataBase.findAccount()
            dataAcc = AccountData(arrAcc[0].id, arrAcc[0].login, arrAcc[0].password,
                arrAcc[0].stat_id)
            navigator().setGamesFragment(dataAcc)
        })
        thr.start()
        binding.navView.setOnNavigationItemSelectedListener(this)
        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_home -> navigator().setGamesFragment(dataAcc)
            R.id.navigation_dashboard -> navigator().setCharactersFragment(dataAcc)
            R.id.navigation_notifications -> navigator().setAccountFragment(dataAcc)
        }
        return true
    }
}