package fileMaker

import vkClient.model.AudioGet
import zio.{Has, IO, ULayer, ZIO, ZLayer}


object FileMaker  {

  type Env = Has[FileMaker.Service]

  trait Service {

    def getAudioListFromJson(a: AudioGet): IO[Throwable, List[String]]

    def makeFile(l: List[String]): IO[Throwable, Long]

  }

  def getAudioFromResponse(a: AudioGet): ZIO[Env, Throwable, List[String]] =
    ZIO.accessM(_.get.getAudioListFromJson(a))

  def makeFile(l: List[String]): ZIO[Env, Throwable, Long] =
    ZIO.accessM(_.get.makeFile(l))

  val live: ULayer[Env] = ZLayer.succeed(new FileMakerImpl)

}
