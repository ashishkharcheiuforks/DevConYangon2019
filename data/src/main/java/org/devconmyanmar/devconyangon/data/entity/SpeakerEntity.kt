package org.devconmyanmar.devconyangon.data.entity

import org.devconmyanmar.devconyangon.domain.mapper.UnidirectionalMap
import org.devconmyanmar.devconyangon.domain.model.Speaker
import org.devconmyanmar.devconyangon.domain.model.SpeakerId
import javax.inject.Inject

/**
 * Created by Vincent on 11/3/19
 */
data class SpeakerEntity(
  val speakerId: SpeakerId,
  val name: String
)

class SpeakerEntityMapper @Inject constructor() : UnidirectionalMap<SpeakerEntity, Speaker> {

  override fun map(item: SpeakerEntity): Speaker {
    return Speaker(
      item.speakerId,
      item.name
    )
  }

}