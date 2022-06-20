package vkClient.client

import vkClient.model.AudioGet
import vkClient.model.AudioGet.Response
import zhttp.service.{ChannelFactory, EventLoopGroup}
import zio.{Has, IO, UIO, ZIO}

object VkClient {

  type Env = Has[VkClient]

  trait VkClient {

    val baseApiUrl = "https://api.vk.com/method/"

    def audioGet(owner_id: String, count: Int): ZIO[EventLoopGroup with ChannelFactory, Throwable, AudioGet]

    def audioGetCount(owner_id: String): ZIO[EventLoopGroup with ChannelFactory, Throwable, Int]

  }

  def audioGet(owner_id: String, count: Int): ZIO[Env with EventLoopGroup with ChannelFactory, Throwable, AudioGet] =
    ZIO.accessM(_.get.audioGet(owner_id, count))

  def audioGetCount(owner_id: String): ZIO[Env with EventLoopGroup with ChannelFactory, Throwable, Int] =
    ZIO.accessM(_.get.audioGetCount(owner_id))
  
}
