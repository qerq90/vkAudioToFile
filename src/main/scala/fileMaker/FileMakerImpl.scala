package fileMaker

import fileMaker.FileMaker.Service
import vkClient.model.AudioGet
import zio.IO
import zio.stream.ZTransducer.gzip
import zio.stream.{ZSink, ZStream}

import java.nio.file.Paths

class FileMakerImpl extends Service {


  override def getAudioListFromJson(a: AudioGet): IO[Throwable, List[String]] = {
    IO
      .fromEither(Right(a.response.items.map(t => t.artist + " - " + t.title)))
  }

  override def makeFile(l: List[String]): IO[Throwable, Long] = {
    val result = l.mkString("\n")
    println(result)
    ZStream
      .fromIterable(l)
      .map(value => value.toByte)
      .transduce(gzip())
      .run(ZSink.fromFile(Paths.get("src/main/resources/file.txt")))
  }
}
