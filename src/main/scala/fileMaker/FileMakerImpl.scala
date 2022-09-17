package fileMaker

import fileMaker.FileMaker.Service
import vkClient.model.AudioGet

import zio.IO
import zio.blocking.Blocking
import zio.stream.{ZSink, ZStream}

import java.nio.file.Paths
import java.nio.charset.StandardCharsets

class FileMakerImpl extends Service {


  private def getAudioListFromJson(a: AudioGet): String = {
    a.response.items.map(t => t.artist + " - " + t.title).mkString("\n")
  }

  override def makeFile(json: AudioGet): IO[Throwable, Long] = {
    val result: String = getAudioListFromJson(json)
    val tempZSink: ZSink[Blocking, Throwable, Byte, Any, Long] =
      ZSink.fromFile(Paths.get("src/main/resources/file.txt"))
    ZStream
      .fromIterable(result.getBytes(StandardCharsets.UTF_8))
      .run(tempZSink)
      .provideLayer(Blocking.live)
  }
}
