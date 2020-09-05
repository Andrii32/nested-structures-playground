
import cats.effect._

object NestedTransformationsPlayground extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    IO(println("Hello World")).as(ExitCode.Success)

  }
}
