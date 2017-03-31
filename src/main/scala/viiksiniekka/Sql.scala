package viiksiniekka

case class Table(name: String, columns: Seq[Column])

case class Column(name: String, sqlType: SqlType, notNull: Boolean, isPrimaryKey: Boolean, foreignKeyConstraint: Option[Column], fields: Seq[Field])

case class SqlType(value: String)
