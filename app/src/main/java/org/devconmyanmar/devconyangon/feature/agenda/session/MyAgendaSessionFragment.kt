package org.devconmyanmar.devconyangon.feature.agenda.session

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.devconmyanmar.devconyangon.R
import org.devconmyanmar.devconyangon.base.core.mvp.MvpFragment
import org.devconmyanmar.devconyangon.base.helper.showShortToast
import org.devconmyanmar.devconyangon.databinding.FragmentMyAgendaSessionBinding
import org.devconmyanmar.devconyangon.domain.helper.Zones
import org.devconmyanmar.devconyangon.domain.model.SessionId
import org.devconmyanmar.devconyangon.feature.schedule.session.SessionFragment
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate

/**
 * Created by Vincent on 11/6/19
 */
class MyAgendaSessionFragment() :
  MvpFragment<MyAgendaSessionView, MyAgendaSessionViewModel>(),
  MyAgendaSessionView, MyAgendaItemClickListener {

  companion object {

    private const val ARG_DATE = "date"

    fun newInstance(localDate: LocalDate): SessionFragment {
      val fragment = SessionFragment()
      val bundle = bundleOf(
        ARG_DATE to localDate.atStartOfDay().atZone(Zones.YANGON).toInstant().toEpochMilli()
      )
      fragment.arguments = bundle
      return fragment
    }
  }

  private val date by lazy {
    val timestamp = requireArguments().getLong(MyAgendaSessionFragment.ARG_DATE)
    Instant.ofEpochMilli(timestamp).atZone(Zones.YANGON).toLocalDate()
  }

  override val viewModel: MyAgendaSessionViewModel by contractedViewModel()

  override val layoutId: Int
    get() = R.layout.fragment_my_agenda_session

  private val binding by lazy {
    FragmentMyAgendaSessionBinding.bind(requireView())
  }

  private val myAgendaSessionAdapter by lazy {
    MyAgendaSessionAdapter(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.setDate(date)

    binding.rvSession.apply {
      adapter = myAgendaSessionAdapter
      layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    viewModel.loadSessions()
  }

  override fun subscribeToMyAgendaSessionViewItemListLiveData(myAgendaSessionViewItemListLiveData: LiveData<List<MyAgendaSessionViewItem>>) {
    myAgendaSessionViewItemListLiveData.observe(viewLifecycleOwner, Observer {
      myAgendaSessionAdapter.submitList(it)
    })
  }

  override fun scrollToIndex(indexToScrollTo: Int) {
    if (indexToScrollTo <= myAgendaSessionAdapter.itemCount - 1) {
      binding.rvSession.scrollToPosition(indexToScrollTo)
    }

  }

  override fun onSessionItemClick(sessionId: SessionId, position: Int) {
    showShortToast("Clicked")
    //TODO: Show Session Detail
  }

  override fun onFavoriteClick(sessionId: SessionId, position: Int) {
    viewModel.toggleFavoriteStatus(sessionId)
  }

}