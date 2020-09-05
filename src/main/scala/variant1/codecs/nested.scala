package variant1.codecs

import variant1.models._

/**
  * Nested  => Flatten
  * Flatten => Nested
  */


trait CodecNested[A] { self =>
  def fromNested(x: Nested): A
  def toNested(x: A): Nested

  def imap[B](f: A => B, g: B => A): CodecNested[B] =
    new CodecNested[B] {
      def fromNested(a: Nested): B = f(self.fromNested(a))
      def toNested(b: B): Nested = self.toNested(g(b))
    }
}

object CodecNested{

    private def fromGroup(meta: MetaData, group: RecordGroup, fields: List[Field] = List.empty[Field]): Set[RecordDirected] =
        group.values.flatMap(
            rec => rec.value match {
                case x: Atom  => List(RecordDirected(Path(List(rec.field)), x))
                case x: RecordGroup => fromGroup(meta=meta, group=x, fields=fields ::: List(rec.field))
            }
        )

    private def toGroup(records: Set[RecordDirected]): RecordGroup = {
            val perRootField = records.groupBy(x => x.path.value.head)
            RecordGroup(values=perRootField
                .map{
                    case (field, records) => records.toList match {
                        case one :: Nil  => Record(field, one.item)
                        case multiple    => Record(field, toGroup(multiple.map(
                            rec => RecordDirected(Path(rec.path.value.tail), rec.item)
                        ).toSet))
                    }
                }.toSet
            )
        }

    implicit val flattenCodec = new CodecNested[Flatten] {
      def fromNested(x: Nested): Flatten =
        Flatten(meta=x.meta, data=RecordDirectedGroup(fromGroup(x.meta, x.data)))
      def toNested(x: Flatten): Nested =
        Nested(meta=x.meta, data=toGroup(x.data.values))
    }

}
