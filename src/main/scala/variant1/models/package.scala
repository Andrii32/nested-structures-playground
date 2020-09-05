package variant1.models

// METADATA
case class MetaData(id: String, chunkId: String, timestamp: Int)

// CORE
sealed trait Data
/**
  * Atom is a discrete, indivisible piece of data
  */
sealed trait Atom  extends Data
sealed trait Group extends Data


// NESTED
case class Field(value: String)
case class Record(field: Field, value: Data)
case class RecordGroup(values: Set[Record]) extends Group
/**
  * Nested is set of Record with common Metadata
  */
case class Nested(meta: MetaData, data: RecordGroup)
case class NestedSet(values: Set[Nested])


// FLATTEN
case class Path(value: List[Field])
case class RecordDirected(path: Path, item: Atom)
case class RecordDirectedGroup(values: Set[RecordDirected])
/**
  * Flatten is set of RecordDirected with common Metadata
  */
case class Flatten(meta: MetaData, data: RecordDirectedGroup)
case class FlattenSet(values: Set[Flatten])


// TRIPLES
/**
  * Triple is a Atom with Metadata and Path to the Atom
  */
case class Triple(meta: MetaData, value: RecordDirected)
case class TripleSet(values: Set[Triple])
