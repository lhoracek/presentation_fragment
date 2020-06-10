package cz.lhoracek.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cz.lhoracek.presentation.databinding.ActivityMainBinding
import cz.lhoracek.presentation.di.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Module
    abstract class InjectionModule {
        @Binds
        @ActivityScope
        internal abstract fun bindAppCompatActivity(activity: MainActivity): AppCompatActivity
    }

    @Inject lateinit var presentationViewModel: PresentationViewModel
    @Inject lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val mgr = applicationContext.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val displays = mgr.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION)
        val display = displays[0]

        val preso = SamplePresentationFragment.newInstance(this, display)
        preso.show(supportFragmentManager, "preso");

        viewModel.navigate.observe(this, Observer {
            startActivity(Intent(this, it))
        })
    }

    override fun onResume() {
        super.onResume()
        presentationViewModel.destination.value = Destination.MAIN
    }
}

class MainViewModel @Inject constructor(): ViewModel(){
    val navigate = SingleLiveEvent<Class<out Activity>>()
    val clickhandler = {navigate.value = SecondActivity::class.java}
}