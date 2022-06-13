import zio._
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import io.circe.parser.decode
import vkClient.model.{AudioGet, AudioGetCount}
import vkClient.client.{VkClientImpl, VkClientParserImpl}


object Main extends App {

  private val env = ChannelFactory.auto ++ EventLoopGroup.auto()
  private val user = "141498951"
  private val vk = new VkClientImpl()

  private val app = for {
    audioCount <- vk.audioGetCount(user)
    audioGet <- vk.audioGet(user, audioCount)
    _ <- console.putStr(audioGet.toString)
  } yield ()


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = app.exitCode.provideCustomLayer(env)
}
