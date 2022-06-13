package vkClient.client

import pureconfig._
import pureconfig.generic.auto._
import io.circe.parser.{decode, parse}
import vkClient.client.VkClient.{Env, VkClient}
import vkClient.config.VkApiConfig
import vkClient.model.{AudioGet, AudioGetCount}
import zhttp.http.{HttpData, Method, Response}
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import zio.{IO, UIO, ZIO}

class VkClientImpl extends VkClient {

  private val vkConfig = ConfigSource
    .resources("application-local.conf")
    .at("vk_api")
    .loadOrThrow[VkApiConfig]

  override def audioGet(owner_id: String, count: Int):
  ZIO[EventLoopGroup with ChannelFactory, Throwable, AudioGet] = {
    val reqBody = HttpData.fromString(
      s"owner_id=$owner_id&" +
        s"access_token=${vkConfig.accessToken}&" +
        s"v=${vkConfig.v}&" +
        s"count=$count"
    )

    for {
      response <- Client.request(
          url = baseApiUrl + "audio.get",
          method = Method.POST,
          content = reqBody
        )
      responseBody <- response.bodyAsString
      answer <- ZIO
        .fromEither(decode[AudioGet](responseBody))
    } yield answer
  }

  override def audioGetCount(owner_id: String):
  ZIO[EventLoopGroup with ChannelFactory, Throwable, Int] = {
    val reqBody = HttpData.fromString(
      s"owner_id=$owner_id&" +
        s"access_token=${vkConfig.accessToken}&" +
        s"v=${vkConfig.v}"
    )

    for {
      response <- Client.request(
        url = s"${baseApiUrl}audio.getCount",
        method = Method.POST,
        content = reqBody
      )
      responseBody <- response.bodyAsString
      answer <- ZIO
        .fromEither(decode[AudioGetCount](responseBody))
        .map(_.response)
   } yield answer

  }

}
