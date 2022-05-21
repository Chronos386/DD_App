package com.example.dd_app.fragments.contact
import androidx.fragment.app.Fragment
import com.example.dd_app.dataSource.AccountData

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun goToMainFrag()
    fun setGamesFragment(acc: AccountData)
    fun setCharactersFragment(acc: AccountData)
    fun setAccountFragment(acc: AccountData)
    fun goToLoginFrag()
}