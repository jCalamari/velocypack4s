import com.typesafe.sbt.SbtPgp.autoImport._
import sbt.Keys._
import sbt._

object Gpg {

  lazy val settings = Seq(
    useGpg := false,
    usePgpKeyHex("38D42FA57C10A00B"),
    pgpPublicRing := baseDirectory.value / "project" / ".gnupg" / "pubring.gpg",
    pgpSecretRing := baseDirectory.value / "project" / ".gnupg" / "secring.gpg",
    pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray)
  )

}
