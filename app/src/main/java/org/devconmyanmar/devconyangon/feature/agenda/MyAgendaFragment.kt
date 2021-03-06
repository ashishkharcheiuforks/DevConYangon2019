package org.devconmyanmar.devconyangon.feature.agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import org.devconmyanmar.devconyangon.base.core.mvp.MvpFragment
import org.devconmyanmar.devconyangon.databinding.FragmentMyAgendaBinding

/**
 * Created by Vincent on 2019-11-02
 */
class MyAgendaFragment : MvpFragment<FragmentMyAgendaBinding, MyAgendaView, MyAgendaViewModel>(),
  MyAgendaView {

  override fun bindView(inflater: LayoutInflater): FragmentMyAgendaBinding =
    FragmentMyAgendaBinding.inflate(inflater)

  override val viewModel: MyAgendaViewModel by contractedViewModel()

  private val myAgendaPagerAdapter by lazy {
    MyAgendaPagerAdapter(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)



    viewModel.loadSessions()
  }

  override fun onPause() {
    binding.viewPager.adapter = null
    super.onPause()

  }

  override fun onResume() {
    super.onResume()
    binding.viewPager.adapter = myAgendaPagerAdapter

    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
      val itemAtIndex = myAgendaPagerAdapter.getItemAtPosition(position)
      tab.text = itemAtIndex.dateAsString
    }.attach()

  }

  override fun subscribeToViewItemListLiveData(viewItemListLiveData: LiveData<List<MyAgendaDateViewItem>>) {
    viewItemListLiveData.observe(viewLifecycleOwner, Observer {
      if (it.isNotEmpty()) {
        myAgendaPagerAdapter.setItems(it)
      } else {

      }

    })
  }

  override fun scrollToIndex(indexToScrollTo: Int) {
    if (indexToScrollTo >= myAgendaPagerAdapter.itemCount - 1) {
      binding.viewPager.setCurrentItem(indexToScrollTo, false)
    }
  }

}