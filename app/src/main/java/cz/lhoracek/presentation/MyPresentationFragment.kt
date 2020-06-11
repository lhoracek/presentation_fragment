package cz.lhoracek.presentation

import android.content.Context
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.commonsware.cwac.preso.PresentationFragment
import com.google.android.material.internal.NavigationMenu
import cz.lhoracek.presentation.databinding.PresentationMainBinding
import cz.lhoracek.presentation.di.ActivityScope
import cz.lhoracek.presentation.di.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


class SamplePresentationFragment : PresentationFragment(), HasAndroidInjector {
    @Module
    abstract class InjectionModule {
        @Binds
        @FragmentScope
        internal abstract fun bindFragment(fragment: SamplePresentationFragment): Fragment
    }

    lateinit var binding: PresentationMainBinding


    @Inject lateinit var viewModel: PresentationViewModel

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any?>
    override fun androidInjector() = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Transformations.distinctUntilChanged(viewModel.destination).observe(this, Observer {
            Navigation.findNavController(binding.root.findViewById(R.id.nav_host_fragment)).navigate(it.id)
            Timber.d("Presentation thinks activity is $activity")
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<PresentationMainBinding>(inflater, R.layout.presentation_main, null, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    companion object {
        fun newInstance(ctxt: Context?, display: Display?): SamplePresentationFragment {
            val frag = SamplePresentationFragment()
            frag.setDisplay(ctxt, display)
            return frag
        }
    }


}

@Singleton
class PresentationViewModel @Inject constructor(): ViewModel() {
    val destination = MutableLiveData<Destination>()
    var displayed = false
}


enum class Destination(val id: Int){
    MAIN(R.id.dest_prstMainFragment),
    SECOND(R.id.dest_prstSecondFragment)
}