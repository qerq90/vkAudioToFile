import fileMaker.FileMaker
import vkClient.client.VkClient
import zio._


object Main extends App {

  private val env = VkClient.live
  private val user = ""

  private val app = for {
    count <- VkClient.audioGetCount(user)
    _ <- console.putStrLn(count.toString)
  } yield ()


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = app.exitCode.provideCustomLayer(env)
}