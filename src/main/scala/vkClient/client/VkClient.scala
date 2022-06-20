package vkClient.client

import core.zhttp.Client.ClientImpl
import vkClient.model.AudioGet
import zio.{Has, IO, ULayer, ZIO, ZLayer}

object VkClient {

  type Env = Has[VkClient.Service]

  trait Service {

    val baseApiUrl = "https://api.vk.com/method/"

    def audioGet(owner_id: String, count: Int): IO[Throwable, AudioGet]

    def audioGetCount(owner_id: String): IO[Throwable, Int]

  }

  def audioGet(owner_id: String, count: Int): ZIO[Env, Throwable, AudioGet] =
    ZIO.accessM(_.get.audioGet(owner_id, count))

  def audioGetCount(owner_id: String): ZIO[Env, Throwable, Int] =
    ZIO.accessM(_.get.audioGetCount(owner_id))

  val live: ULayer[Env] = ZLayer.succeed(new VkClientImpl(ClientImpl))
  
}
