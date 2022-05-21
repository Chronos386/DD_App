package com.example.dd_app
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.databinding.ActivityMainBinding
import com.example.dd_app.fragments.LoginFragment
import com.example.dd_app.fragments.MainFragment
import com.example.dd_app.fragments.barFragments.AccountFragment
import com.example.dd_app.fragments.barFragments.CharactersFragment
import com.example.dd_app.fragments.contact.Navigator
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.isOnline
import com.example.dd_app.fragments.barFragments.GameFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Navigator {
    @Inject lateinit var dataBase: DataFromDB
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        dataBase.initDataBase(this)
        setContentView(binding.root)
        if(isOnline(this)) {
            if (savedInstanceState == null) {
                val thr = Thread(kotlinx.coroutines.Runnable {
                    if (dataBase.findCountAccount() == 0) {
                        supportFragmentManager
                            .beginTransaction()
                            .add(R.id.mainFragmentContainer, LoginFragment())
                            .commit()
                    } else {
                        supportFragmentManager
                            .beginTransaction()
                            .add(R.id.mainFragmentContainer, MainFragment())
                            .commit()
                    }
                })
                thr.start()
            }
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun goToMainFrag(){
        if(isOnline(this)) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainFragmentContainer, MainFragment())
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setGamesFragment(acc: AccountData) {
        if(isOnline(this)) {
            val fragment = GameFragment.newInstance(
                item = acc
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setCharactersFragment(acc: AccountData) {
        if(isOnline(this)) {
            val fragment = CharactersFragment.newInstance(
                item = acc
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setAccountFragment(acc: AccountData) {
        if(isOnline(this)) {
            val fragment = AccountFragment.newInstance(
                    item = acc
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun goToLoginFrag(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, LoginFragment())
            .commit()
    }
}