package org.devconmyanmar.devconyangon.domain.usecase

import org.devconmyanmar.devconyangon.domain.CoroutineUseCase
import org.devconmyanmar.devconyangon.domain.model.Session
import org.devconmyanmar.devconyangon.domain.repository.SessionRepository
import org.threeten.bp.LocalDate
import javax.inject.Inject

/**
 * Created by Vincent on 2019-11-02
 */
class GetSessions @Inject constructor(
  private val sessionRepository: SessionRepository
) :
  CoroutineUseCase<GetSessions.Params, List<Session>>() {

  override suspend fun provide(params: Params): List<Session> {

    return sessionRepository.getSessionListing(params.date)
  }



  data class Params(
    val date: LocalDate
  )
}