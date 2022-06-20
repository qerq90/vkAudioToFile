package core.zhttp

import zio._
import zhttp.service.ChannelFactory
import zhttp.service.EventLoopGroup
import zhttp.http.Method
import zhttp.http.HttpData
import zhttp.http.Headers
import zhttp.http.Response
import zhttp.service.{ Client => ZClient }

object Client {
    type Env = Has[Service]

    trait Service {
        def request(url: String,
                    method: Method,
                    headers: Headers,
                    content: HttpData): IO[Throwable, Response]
    }

    def request(url: String,
                method: Method = Method.GET,
                headers: Headers = Headers.empty,
                content: HttpData = HttpData.empty): ZIO[Env, Throwable, Response] = 
        ZIO.accessM(_.get.request(url, method, headers, content))
  
    case object ClientImpl extends Service {
			private val env = ChannelFactory.auto ++ EventLoopGroup.auto()

			override def request(url: String, method: Method, headers: Headers, content: HttpData): IO[Throwable,Response] = 
        ZClient.request(url, method, headers, content).provideLayer(env)
    }

		val live: ULayer[Env] = ZLayer.succeed(ClientImpl)
}
