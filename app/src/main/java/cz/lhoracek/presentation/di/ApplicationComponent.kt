package cz.lhoracek.presentation.di

import android.app.Application
import cz.lhoracek.presentation.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBindingModule::class, FragmentBindingModule::class])
abstract class ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AndroidInjector<App>
    }
}

