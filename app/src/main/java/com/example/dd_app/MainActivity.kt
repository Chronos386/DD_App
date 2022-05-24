package com.example.dd_app
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.CharacterData
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.databinding.ActivityMainBinding
import com.example.dd_app.fragments.*
import com.example.dd_app.fragments.barFragments.AccountFragment
import com.example.dd_app.fragments.barFragments.CharactersFragment
import com.example.dd_app.fragments.contact.Navigator
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.isOnline
import com.example.dd_app.fragments.barFragments.GameFragment
import com.example.dd_app.fragments.baseFragments.*
import com.example.dd_app.fragments.generalBarFragments.*
import com.example.dd_app.fragments.masterBarFragments.GamersCharactersInGameFragment
import com.example.dd_app.fragments.masterBarFragments.GamersFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Navigator {
    @Inject lateinit var dataBase: DataFromDB
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
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

    override fun goToMasterGamerCharactersFrag(accGamer: AccountData, accMaster: AccountData,
                                               game: GameData) {
        if(isOnline(this)) {
            val fragment = GamersCharactersInGameFragment.newInstance(
                gamer = accGamer,
                master = accMaster,
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

    override fun goToCharacterInfoFrag(acc: AccountData, character: CharacterData,
                                       masterName: String) {
        if(isOnline(this)) {
            val fragment = CharacterFragment.newInstance(
                account = acc,
                characterItem = character,
                masterName = masterName
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

    override fun goToRedactCharacterFrag(character: CharacterData) {
        if(isOnline(this)) {
            val fragment = RedactCharacterFragment.newInstance(
                character = character
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

    override fun goToSpellsFrag(classID: Long) {
        if(isOnline(this)) {
            val fragment = SpellsFragment.newInstance(
                classID = classID
            )
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.mainFragmentContainer, fragment)
                .commit()
        }
        else {
            val toastTXT = "Отсутствует подключение к интернету"
            Toast.makeText(applicationContext, toastTXT, Toast.LENGTH_LONG).show()
        }
    }

    override fun goToNewCharacterFrag(item: AccountData, gameItem: GameData, ownerID: Long) {
        if(isOnline(this)) {
            val fragment = CreateCharacterFragment.newInstance(
                item = item,
                gameItem = gameItem,
                ownerID = ownerID
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