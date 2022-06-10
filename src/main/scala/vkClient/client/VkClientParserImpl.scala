package vkClient.client

import vkClient.client.VkClientParser.VkClientParser
import io.circe.parser.decode
import vkClient.model.{AudioGet, AudioGetCount}
import zhttp.http.Response
import zio.{IO, ZIO}

class VkClientParserImpl extends VkClientParser {

  override def audioGetCountParser(r: Response): IO[Throwable, Int] =
    for {
      responseBody <- r.bodyAsString
      result <- ZIO
        .fromEither(decode[AudioGetCount](responseBody))
        .map(_.response)
    } yield result

  override def audioGetParser(r: Response): IO[Throwable, List[String]] =
    for {
      responseBody <- r.bodyAsString
      result <- ZIO
        .fromEither(decode[AudioGet](responseBody))
        .map(_.response
          .items
          .map(track => track.artist + " - " + track.title))
    } yield result

}
