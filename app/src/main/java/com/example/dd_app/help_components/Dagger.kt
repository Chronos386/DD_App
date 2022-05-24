package com.example.dd_app.help_components
import com.example.dd_app.MainActivity
import com.example.dd_app.dataFrom.DataFromDB
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.fragments.*
import com.example.dd_app.fragments.barFragments.AccountFragment
import com.example.dd_app.fragments.barFragments.CharactersFragment
import com.example.dd_app.fragments.barFragments.GameFragment
import com.example.dd_app.fragments.baseFragments.*
import com.example.dd_app.fragments.dialogFragments.*
import com.example.dd_app.fragments.dialogFragments.sureMake.SureDelAccountFragment
import com.example.dd_app.fragments.dialogFragments.sureMake.SureDelCharacterFragment
import com.example.dd_app.fragments.dialogFragments.sureMake.SureDelGameFragment
import com.example.dd_app.fragments.dialogFragments.sureMake.SureDelGamerFragment
import com.example.dd_app.fragments.dialogFragments.whatDoWith.WhatDoWithGameFragment
import com.example.dd_app.fragments.dialogFragments.whatDoWith.WhatDoWithGamerFragment
import com.example.dd_app.fragments.generalBarFragments.GameCharactersFragment
import com.example.dd_app.fragments.masterBarFragments.GamersCharactersInGameFragment
import com.example.dd_app.fragments.masterBarFragments.GamersFragment
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
    fun inject(fragment: SpellsFragment)
    fun inject(fragment: MasterSpecificGameFragment)
    fun inject(fragment: GamerSpecificGameFragment)
    fun inject(fragment: GameCharactersFragment)
    fun inject(fragment: CreateCharacterFragment)
    fun inject(fragment: GamersCharactersInGameFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: GamersFragment)
    fun inject(fragment: SureDelAccountFragment)
    fun inject(fragment: UpdAccountPswFragment)
    fun inject(fragment: CharacterFragment)
    fun inject(fragment: WhatDoWithGameFragment)
    fun inject(fragment: SureDelCharacterFragment)
    fun inject(fragment: WhatDoWithGamerFragment)
    fun inject(fragment: RedactCharacterFragment)
    fun inject(fragment: SureDelGameFragment)
    fun inject(fragment: SureDelGamerFragment)
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: GameFragment)
    fun inject(fragment: JoinGameFragment)
    fun inject(fragment: CreateNewGameFragment)
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