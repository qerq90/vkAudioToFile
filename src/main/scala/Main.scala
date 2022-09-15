import fileMaker.FileMaker
import vkClient.client.VkClient
import zio._


object Main extends App {

  private val env = FileMaker.live ++ VkClient.live
  private val user = "" // DON'T FORGET TO ADD USER HERE

  private val app = for {
    count <- VkClient.audioGetCount(user)
    audio <- VkClient.audioGet(user, count)
    _ <- FileMaker.makeFile(audio)
  } yield ()


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = app.exitCode.provideCustomLayer(env)
}