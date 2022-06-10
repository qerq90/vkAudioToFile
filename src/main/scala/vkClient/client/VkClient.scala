package vkClient.client

import zhttp.http.Response
import zhttp.service.{ChannelFactory, EventLoopGroup}
import zio.{Has, IO, ZIO}

object VkClient {

  type Env = Has[VkClient]

  trait VkClient {

    val baseApiUrl = "https://api.vk.com/method/"

    def audioGet(owner_id: String, count: Int): IO[Throwable, List[String]]

    def audioGetCount(owner_id: String): IO[Throwable, Int]

  }

  def audioGet(owner_id: String, count: Int): ZIO[Env, Throwable, List[String]] =
    ZIO.accessM(_.get.audioGet(owner_id, count))

  def audioGetCount(owner_id: String): ZIO[Env, Throwable, Int] =
    ZIO.accessM(_.get.audioGetCount(owner_id))
  
}
