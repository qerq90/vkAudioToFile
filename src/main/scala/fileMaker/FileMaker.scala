package fileMaker

import vkClient.model.AudioGet
import zio.{Has, IO, ULayer, ZIO, ZLayer}


object FileMaker  {

  type Env = Has[FileMaker.Service]

  trait Service {

    def makeFile(l: AudioGet): IO[Throwable, Long]

  }

  def makeFile(l: AudioGet): ZIO[Env, Throwable, Long] =
    ZIO.accessM(_.get.makeFile(l))

  val live: ULayer[Env] = ZLayer.succeed(new FileMakerImpl)

}
