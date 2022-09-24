package vkClient

import vkClient.model.AudioGet
import zio.{Has, IO, ULayer, ZIO, ZLayer}
import core.zhttp.Client.{ Env => zhttpServiceClient }

object VkClient {

  type Env = Has[VkClient.Service]

  trait Service {

    def audioGet(owner_id: String, count: Int): IO[Throwable, AudioGet]

    def audioGetCount(owner_id: String): IO[Throwable, Int]

  }

  def audioGet(owner_id: String, count: Int): ZIO[Env, Throwable, AudioGet] =
    ZIO.accessM(_.get.audioGet(owner_id, count))

  def audioGetCount(owner_id: String): ZIO[Env, Throwable, Int] =
    ZIO.accessM(_.get.audioGetCount(owner_id))

  val live: ZLayer[zhttpServiceClient, Nothing, Env] = 
    ZLayer.fromService(VkClientImpl)
  
}
