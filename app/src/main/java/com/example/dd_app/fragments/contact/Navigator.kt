package com.example.dd_app.fragments.contact
import androidx.fragment.app.Fragment
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.CharacterData
import com.example.dd_app.dataSource.GameData

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun goToMainFrag()
    fun goToGamerGameFrag(acc: AccountData, game: GameData)
    fun goToMasterGameFrag(acc: AccountData, game: GameData)
    fun setGamerDiceFragment(acc: AccountData, game: GameData)
    fun setMasterDiceFragment(acc: AccountData, game: GameData)
    fun setGamerHomeFragment(acc: AccountData, game: GameData)
    fun setMasterHomeFragment(acc: AccountData, game: GameData)
    fun setMasterGamersFragment(acc: AccountData, game: GameData)
    fun goToCharacterInfoFrag(acc: AccountData, character: CharacterData, masterName: String)
    fun goToMasterGamerCharactersFrag(accGamer: AccountData, accMaster: AccountData, game: GameData)
    fun setGamerGameCharactersFragment(acc: AccountData, game: GameData)
    fun setMasterGameCharactersFragment(acc: AccountData, game: GameData)
    fun setGamesFragment(acc: AccountData)
    fun setCharactersFragment(acc: AccountData)
    fun setAccountFragment(acc: AccountData)
    fun goToLoginFrag()
}