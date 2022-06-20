import vkClient.client._
import fileMaker._
import zio.Has
import zio._

object FileCreator {

  type CreatorEnv = Has[FileCreator.Service]

  class Service(client: VkClient.Service, fMaker: FileMaker.Service) {
    def makeNewListOfAudios(user: String): IO[Throwable, Unit] = {
      for {
        getAudioCount <- client.audioGetCount(user)
        getAudio <- client.audioGet(user, getAudioCount)
        audioListForFile <- fMaker.getAudioListFromJson(getAudio)
        _ <- fMaker.makeFile(audioListForFile)
      } yield ()
    }
  }

  def makeNewListOfAudios(user: String): ZIO[CreatorEnv, Nothing, Unit] =
    ZIO.accessM(_.get.makeNewListOfAudios(user))

  val live: ZLayer[VkClient.Env with FileMaker.Env, Nothing, CreatorEnv] =
    ZLayer.fromServices[VkClient.Service, FileMaker.Service, FileCreator.Service]{
      (client, flMkr) => new Service(client, flMkr)
    }
}
