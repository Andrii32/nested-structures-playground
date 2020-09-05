package variant1.codecs

import variant1.models._

/**
  * Flatten => Nested
  * Nested  => Flatten
  */


trait CodecFlatten[A] { self =>
  def fromFlatten(x: Flatten): A
  def toFlatten(x: A): Flatten

  def imap[B](f: A => B, g: B => A): CodecFlatten[B] =
    new CodecFlatten[B] {
      def fromFlatten(a: Flatten): B = f(self.fromFlatten(a))
      def toFlatten(b: B): Flatten = self.toFlatten(g(b))
    }
}

object CodecFlatten{

    implicit def nestedCodec(implicit codec: CodecNested[Flatten]) = new CodecFlatten[Nested] {

      def fromFlatten(x: Flatten): Nested = codec.toNested(x)

      def toFlatten(x: Nested): Flatten = codec.fromNested(x)

    }

}


