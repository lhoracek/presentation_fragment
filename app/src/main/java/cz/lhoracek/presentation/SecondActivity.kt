package cz.lhoracek.presentation

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    lateinit var presentationViewModel: PresentationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val mgr = applicationContext.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val displays = mgr.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION)
        val display = displays[0]

        val preso = SamplePresentationFragment.newInstance(this, display)
        preso.show(getSupportFragmentManager(), "preso");
    }

    override fun onResume() {
        super.onResume()
        presentationViewModel.destination.value = Destination.SECOND
    }
}