package vkClient.model

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class AudioGetCount(response: Int)

object AudioGetCount {
  implicit val AudioGetCount: Decoder[AudioGetCount] = deriveDecoder
}
