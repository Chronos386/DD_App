package com.example.dd_app.help_components
import com.example.dd_app.MainActivity
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.fragments.LoginFragment
import com.example.dd_app.fragments.MainFragment
import com.example.dd_app.fragments.barFragments.AccountFragment
import com.example.dd_app.fragments.barFragments.CharactersFragment
import com.example.dd_app.fragments.barFragments.GameFragment
import com.example.dd_app.fragments.dialogFragments.DelAccountDialogFragment
import com.example.dd_app.fragments.dialogFragments.JoinGameDialogFragment
import com.example.dd_app.fragments.dialogFragments.UpdAccountPswFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DBModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: MainFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: DelAccountDialogFragment)
    fun inject(fragment: UpdAccountPswFragment)
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: GameFragment)
    fun inject(fragment: JoinGameDialogFragment)
}

@Module
class NetworkModule {
    @Provides
    fun provideDataFromNetwork(): DataFromNetwork {
        return DataFromNetwork()
    }
}

@Module
class DBModule {
    @Provides
    fun provideDataFromDB(): DataFromDB {
        return DataFromDB()
    }
}