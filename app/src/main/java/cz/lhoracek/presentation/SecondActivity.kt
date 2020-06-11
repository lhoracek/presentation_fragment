package cz.lhoracek.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cz.lhoracek.presentation.databinding.ActivityMainBinding
import cz.lhoracek.presentation.databinding.ActivitySecondBinding
import cz.lhoracek.presentation.di.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SecondActivity : DaggerAppCompatActivity() {
    @Module
    abstract class InjectionModule {
        @Binds
        @ActivityScope
        internal abstract fun bindAppCompatActivity(activity: SecondActivity): AppCompatActivity
    }

    @Inject
    lateinit var viewModel: SecondViewModel
    @Inject
    lateinit var presentationViewModel: PresentationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySecondBinding>(this, R.layout.activity_second)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigate.observe(this, Observer {
            startActivity(Intent(this, it))
            finish()
        })
    }

    override fun onResume() {
        super.onResume()
        presentationViewModel.destination.value = Destination.SECOND
    }
}

class SecondViewModel @Inject constructor() : ViewModel() {
    val navigate = SingleLiveEvent<Class<out Activity>>()
    val clickhandler = { navigate.value = MainActivity::class.java }
}