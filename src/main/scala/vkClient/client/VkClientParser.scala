package vkClient.client

import zhttp.http.Response
import zio.{Has, IO, ZIO}

object VkClientParser {

  type Env = Has[VkClientParser]

  trait VkClientParser {

    def audioGetCountParser(r: Response): IO[Throwable, Int]

    def audioGetParser(r: Response): IO[Throwable, List[String]]

  }

  def audioGetCountParser(r: Response): ZIO[Env, Throwable, Int] =
    ZIO.accessM(_.get.audioGetCountParser(r))

  def audioGetParser(r: Response): ZIO[Env, Throwable, List[String]] =
    ZIO.accessM(_.get.audioGetParser(r))

}
