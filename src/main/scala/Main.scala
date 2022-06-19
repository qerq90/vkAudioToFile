import zio._
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import io.circe.parser.decode
import vkClient.model.{AudioGet, AudioGetCount}
import vkClient.client.{VkClientImpl, VkClientParserImpl}


object Main extends App {

  private val env = ChannelFactory.auto ++ EventLoopGroup.auto()
  private val user = ""
  // private val vk = new VkClientImpl()
  private val vkParser = new VkClientParserImpl()

  private val app = for {
    // audioCount <- vk.audioGetCount(user)
    // count <- vkParser.audioGetCountParser(audioCount)
    // audioGet <- vk.audioGet(user, count)
    // audioList <- vkParser.audioGetParser(audioGet)
    // _ <- console.putStrLn(audioList
    //   .mkString("\n"))

    _ <- ZIO { println("Hello world!") }
  } yield ()


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = app.exitCode.provideCustomLayer(env)
}
