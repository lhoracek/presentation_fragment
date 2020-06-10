package cz.lhoracek.presentation.di

import cz.lhoracek.presentation.MainActivity
import cz.lhoracek.presentation.SecondActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention
annotation class ActivityScope

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [MainActivity.InjectionModule::class])
    @ActivityScope
    abstract fun firstActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SecondActivity.InjectionModule::class])
    @ActivityScope
    abstract fun secondActivity(): SecondActivity
}