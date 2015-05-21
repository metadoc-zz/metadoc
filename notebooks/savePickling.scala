import scala.pickling.Defaults._
import scala.pickling.binary._

import java.nio.file.{Path, Paths, Files}

val f = new File("metadoc.bin")
val fw = new FileWriter(f)
fw.write(out.pickle)
// f.close()
fw.close()

Files.write(Paths.get("metadoc.bin"), out.pickle.value)

out.pickle.value
Map(1 -> 2).
List(1).pickle.value

println(out)