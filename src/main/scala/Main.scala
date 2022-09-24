import fileMaker.FileMaker
import vkClient.VkClient
import zio._
import core.zhttp.Client


object Main extends App {

  private val env = FileMaker.live ++ (Client.live >>> VkClient.live)
  private val user = "" // DON'T FORGET TO ADD USER HERE

  private val app = for {
    count <- VkClient.audioGetCount(user)
    audio <- VkClient.audioGet(user, count)
    _ <- FileMaker.makeFile(audio)
  } yield ()


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = 
    app.exitCode.provideCustomLayer(env)
}