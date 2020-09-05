package variant1.codecs

import variant1.models._

/**
  * FlattenSet => TripleSet
  * TripleSet  => FlattenSet
  *
  * NestedSet  => TripleSet
  * TripleSet  => NestedSet
  */


trait CodecTripleSet[A] { self =>
  def fromTripleSet(x: TripleSet): A
  def toTripleSet(x: A): TripleSet

  def imap[B](f: A => B, g: B => A): CodecTripleSet[B] =
    new CodecTripleSet[B] {
      def fromTripleSet(a: TripleSet): B = f(self.fromTripleSet(a))
      def toTripleSet(b: B): TripleSet = self.toTripleSet(g(b))
    }
}

object CodecTripleSet{

    implicit def flattenSetCodec = new CodecTripleSet[FlattenSet] {
        def fromTripleSet(x: TripleSet): FlattenSet =
             FlattenSet(
                x.values
                    .groupMap(v => v.meta)(v => v.value)
                    .map{
                        case (meta, records) => Flatten(meta, RecordDirectedGroup(records))
                    }.toSet
             )

        def toTripleSet(x: FlattenSet): TripleSet =
            TripleSet(
                x.values.flatMap(v => v.data.values.map(x => Triple(v.meta, x)))
            )
    }

    implicit def nestedSetCodec(implicit
                                codec1: CodecTripleSet[FlattenSet],
                                codec2: CodecNested[Flatten]
                                ) = new CodecTripleSet[NestedSet] {
        def fromTripleSet(x: TripleSet): NestedSet =
            NestedSet(codec1.fromTripleSet(x).values.map(f => codec2.toNested(f)))

        def toTripleSet(x: NestedSet): TripleSet =
            TripleSet(
                x.values
                    .flatMap(n => codec1.toTripleSet(FlattenSet(Set(codec2.fromNested(n)))).values)
            )

    }

}