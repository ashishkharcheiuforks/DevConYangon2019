package org.devconmyanmar.devconyangon.feature.sessiondetail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.devconmyanmar.devconyangon.base.di.viewmodel.ViewModelKey

/**
 * Created by Vincent on 11/12/19
 */
@Module
abstract class SessionDetailFeatureModule {

  @ContributesAndroidInjector
  abstract fun sessionDetailFragment(): SessionDetailFragment

  @Binds
  @IntoMap
  @ViewModelKey(SessionDetailViewModel::class)
  abstract fun sessionDetailViewModel(sessionDetailViewModel: SessionDetailViewModel): ViewModel
}
