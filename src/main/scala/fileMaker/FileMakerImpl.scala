package fileMaker

import fileMaker.FileMaker.Service
import vkClient.model.AudioGet
import zio.IO
import zio.blocking.Blocking
import zio.stream.ZTransducer.{gzip, usASCIIDecode, utf8Decode, utfDecode}
import zio.stream.{ZSink, ZStream}

import java.nio.file.Paths

class FileMakerImpl extends Service {


  override def getAudioListFromJson(a: AudioGet): IO[Throwable, List[String]] = {
    IO
      .fromEither(Right(a.response.items.map(t => t.artist + " - " + t.title)))
  }

  override def makeFile(l: List[String]): IO[Throwable, Long] = {
    val result = l.mkString("\n")
//    println(result)
    val tempZSink: ZSink[Blocking, Throwable, Byte, Any, Long] = ZSink.fromFile(Paths.get("src/main/resources/file.txt"))
    ZStream
      .fromIterable(result)
      .map(value => value.toByte)
      .transduce(utf8Decode)
        .flatMap(x => ZStream.fromIterable(x))
      .map(value => value.toByte)
      .run(tempZSink)
      .provideLayer(Blocking.live)
  }
}
