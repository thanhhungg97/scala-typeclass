package example

trait SerderAdhoc[A] {
  def serialize(v: A): String

}

object SerderAdhocInstances {

  case class UserAgent(family: String, major: Option[String] = None, minor: Option[String] = None,
                       patch: Option[String] = None)

  implicit val userAgentSerdeAdhocInstance = new SerderAdhoc[UserAgent] {
    override def serialize(userAgent: UserAgent): String = {
      val version =
        s"${Seq(userAgent.major, userAgent.minor, userAgent.patch).map(_.getOrElse("")).takeWhile(_.nonEmpty).mkString(".")}"
      if (version.nonEmpty) s"${userAgent.family}/$version" else userAgent.family
    }
  }
}

object SerderAdhocSyntax {
  implicit class SerderAdhocOps[A](v: A) {
    def serialize(implicit m: SerderAdhoc[A]): String = {
      m.serialize(v)
    }
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    import SerderAdhocInstances._
    import SerderAdhocSyntax._
    UserAgent("test").serialize
  }
}
