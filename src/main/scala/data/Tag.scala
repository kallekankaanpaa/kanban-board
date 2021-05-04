package data

@SerialVersionUID(1L)
case class Tag(identifier: String, description: String) extends Serializable {
  override def toString(): String = identifier
}

object Tag {
  def apply(identifier: String) = new Tag(identifier, "")
}
