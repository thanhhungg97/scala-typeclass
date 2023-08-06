package monoids

// Application for monoids
// Big data:
// We might want to do over a large data set.
// Distributed  System.


trait SemiGroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends SemiGroup[A] {
  def empty: A
}
object Laws{
  def associativeLaw[A](x: A, y: A, z: A)
                       (implicit m: Monoid[A]): Boolean = {
    m.combine(x, m.combine(y, z)) ==
      m.combine(m.combine(x, y), z)
  }

  def identityLaw[A](x: A)
                    (implicit m: Monoid[A]): Boolean = {
    (m.combine(x, m.empty) == x) &&
      (m.combine(m.empty, x) == x)
  }
}
object MonoidsInstance {
  implicit val booleanAndMonoids = new Monoid[Boolean] {
    override def empty: Boolean = true

    override def combine(x: Boolean, y: Boolean): Boolean = x && y
  }
  implicit val booleanOrMonoids = new Monoid[Boolean] {
    override def empty: Boolean = false

    override def combine(x: Boolean, y: Boolean): Boolean = x || y
  }
}

object Monoid {
  def apply[A](implicit monoids: Monoid[A]): Monoid[A] = monoids

}
