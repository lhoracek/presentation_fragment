package cz.lhoracek.presentation.di

import cz.lhoracek.presentation.SamplePresentationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Scope
@Retention
annotation class FragmentScope

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector(modules = [SamplePresentationFragment.InjectionModule::class])
    @FragmentScope
    abstract fun samplePresentation(): SamplePresentationFragment
}