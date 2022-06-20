package vkClient.client

import pureconfig._
import pureconfig.generic.auto._
import io.circe.parser.decode
import vkClient.client.VkClient.Service
import vkClient.config.VkApiConfig
import vkClient.model.{AudioGet, AudioGetCount}
import zhttp.http.{HttpData, Method}
import zio.{IO, ZIO}
import zhttp.http.Headers

class VkClientImpl(httpClient: core.zhttp.Client.Service) extends Service {

  private val vkConfig = ConfigSource
    .resources("application-local.conf")
    .at("vk_api")
    .loadOrThrow[VkApiConfig]

  override def audioGet(owner_id: String, count: Int):
  IO[Throwable, AudioGet] = {
    val reqBody = HttpData.fromString(
      s"owner_id=$owner_id&" +
        s"access_token=${vkConfig.accessToken}&" +
        s"v=${vkConfig.v}&" +
        s"count=$count"
    )
    for {
      response <- httpClient
        .request(
          url = baseApiUrl + "audio.get",
          method = Method.POST,
          content = reqBody,
          headers = Headers.empty
        )
      responseBody <- response.bodyAsString
      parsedBody <- ZIO
        .fromEither(decode[AudioGet](responseBody))
    } yield parsedBody

  }

  override def audioGetCount(owner_id: String):
  IO[Throwable, Int] = {
    val reqBody = HttpData.fromString(
      s"owner_id=$owner_id&" +
        s"access_token=${vkConfig.accessToken}&" +
        s"v=${vkConfig.v}"
    )
    for {
      response <- httpClient.request(
        url = s"${baseApiUrl}audio.getCount",
        method = Method.POST,
        content = reqBody,
        headers = Headers.empty,
      )
      responseBody <- response.bodyAsString
      parsedBody <- ZIO
        .fromEither(decode[AudioGetCount](responseBody))
        .map(_.response)
    } yield parsedBody
  }

}