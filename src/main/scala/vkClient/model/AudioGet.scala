package vkClient.model

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import AudioGet._

case class AudioGet(response: Response)

object AudioGet {

  case class Response(count: Int, items: List[TrackInfo])

  case class TrackInfo(artist: String, title: String)

  implicit val AudioGetDecoder: Decoder[AudioGet] = deriveDecoder
  implicit val ResponseDecoder: Decoder[Response] = deriveDecoder
  implicit val TrackInfoDecoder: Decoder[TrackInfo] = deriveDecoder
}
