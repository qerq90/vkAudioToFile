package fileMaker

import fileMaker.FileMaker.Service
import vkClient.model.AudioGet
import zio.nio.core.charset.Charset
import zio.nio.core.file.Path
import zio.{IO, ZIO}
import zio.nio.file.Files

class FileMakerImpl extends Service {


  override def getAudioListFromJson(a: AudioGet): IO[Throwable, List[String]] = {
    IO
      .fromEither(Right(a.response.items.map(t => t.artist + " - " + t.title)))
  }

  override def makeFile(l: List[String]): IO[Throwable, Unit] = {
    val allAudios = l.mkString("\n")
    println(allAudios)
    for {
      encodeAudiosUtf8 <- Charset.Standard.utf8.encodeString(allAudios)
      _ <- ZIO.fromEither(Right(Files.writeBytes(Path("musicFile.txt"), encodeAudiosUtf8)))
    } yield ()
  }

}
