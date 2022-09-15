package fileMaker

import fileMaker.FileMaker.Service
import vkClient.model.AudioGet
import zio.IO
import zio.blocking.Blocking
import zio.stream.ZTransducer.utf8Decode
import zio.stream.{ZSink, ZStream}
import zio.nio.core.charset.Charset

import java.nio.file.Paths

class FileMakerImpl extends Service {


  private def getAudioListFromJson(a: AudioGet): String = {
    a.response.items.map(t => t.artist + " - " + t.title).mkString("\n")
  }

  override def makeFile(json: AudioGet): IO[Throwable, Long] = {
    val result: String = getAudioListFromJson(json)
    val tempZSink: ZSink[Blocking, Throwable, Byte, Any, Long] =
      ZSink.fromFile(Paths.get("src/main/resources/file.txt"))
    ZStream
      .fromIterable(result)
      .map(value => value.toByte)
      .transduce(Charset.Standard.utf8.newDecoder.transducer())
      .map(value => value.toByte)
      .run(tempZSink)
      .provideLayer(Blocking.live)
  }
}
