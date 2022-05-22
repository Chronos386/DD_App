package com.example.dd_app
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.ActivityMainBinding
import com.example.dd_app.fragments.GamerSpecificGameFragment
import com.example.dd_app.fragments.LoginFragment
import com.example.dd_app.fragments.MainFragment
import com.example.dd_app.fragments.MasterSpecificGameFragment
import com.example.dd_app.fragments.barFragments.AccountFragment
import com.example.dd_app.fragments.barFragments.CharactersFragment
import com.example.dd_app.fragments.contact.Navigator
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.isOnline
import com.example.dd_app.fragments.barFragments.GameFragment
import com.example.dd_app.fragments.generalBarFragments.DiceFragment
import com.example.dd_app.fragments.generalBarFragments.GameCharactersFragment
import com.example.dd_app.fragments.generalBarFragments.HomeFragment
import com.example.dd_app.fragments.masterBarFragments.GamersFragment
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

    override fun goToMainFrag() {
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

    override fun goToGamerGameFrag(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = GamerSpecificGameFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.mainFragmentContainer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun goToMasterGameFrag(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = MasterSpecificGameFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.mainFragmentContainer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setGamerDiceFragment(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = DiceFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerGamer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setMasterDiceFragment(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = DiceFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerMaster, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setGamerGameCharactersFragment(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = GameCharactersFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerGamer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setMasterGameCharactersFragment(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = GameCharactersFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerMaster, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setGamerHomeFragment(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = HomeFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerGamer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setMasterHomeFragment(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = HomeFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerMaster, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun setMasterGamersFragment(acc: AccountData, game: GameData) {
        if(isOnline(this)) {
            val fragment = GamersFragment.newInstance(
                item = acc,
                gameItem = game
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerMaster, fragment)
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