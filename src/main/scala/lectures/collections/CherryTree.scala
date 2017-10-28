package lectures.collections

import lectures.collections.CherryTree.{Node, Node1, Node2}

import scala.collection.generic._
import scala.collection.{GenTraversableOnce, LinearSeq, LinearSeqOptimized, mutable}

sealed trait CherryTree[+T] extends LinearSeq[T]
  with LinearSeqOptimized[T, CherryTree[T]]
  with GenericTraversableTemplate[T, CherryTree]
  with Product with Serializable {

  override def init: CherryTree[T] = this match {
    case p: CherrySingle[T] => p.initCase
    case p: CherryBranch[T] => p.initCase
    case _ => throw new UnsupportedOperationException()
  }

  override def last: T = this match {
    case p: CherrySingle[T] => p.lastCase
    case p: CherryBranch[T] => p.last
    case _ => throw new NoSuchElementException()
  }

  override def tail: CherryTree[T] = this match {
    case p: CherrySingle[T] => p.tailCase
    case p: CherryBranch[T] => p.tailCase
    case _ => throw new UnsupportedOperationException()
  }

  def append[S >: T](x: S): CherryTree[S]

//  override def apply(n: Int): T = {
//    var this_copy = this
//    (0 until n).foreach(_ => this_copy = this_copy.tail)
//    this_copy.last
//  }

  def prepend[S >: T](x: S): CherryTree[S]

  def concat[S >: T](xs: CherryTree[S]): CherryTree[S] = {
    if (this.length >= xs.length)
      CherryTree.concateThis(this, xs)
    else
      CherryTree.concateThis(this, xs)
  }


  override def toString(): String = super.toString()

  override def companion = CherryTree

  override def stringPrefix: String = "CherryTree"


  // If we have a default builder, there are faster ways to perform some operations
  @inline private[this] def isDefaultCBF[A, B, That](bf: CanBuildFrom[CherryTree[A], B, That]): Boolean = bf eq CherryTree.ReusableCBF

  override def :+[B >: T, That](elem: B)(implicit bf: CanBuildFrom[CherryTree[T], B, That]) =
    if (isDefaultCBF(bf)) append(elem).asInstanceOf[That] else super.:+(elem)

  override def +:[B >: T, That](elem: B)(implicit bf: CanBuildFrom[CherryTree[T], B, That]) =
    if (isDefaultCBF(bf)) prepend(elem).asInstanceOf[That] else super.:+(elem)

  override def ++[B >: T, That](that: GenTraversableOnce[B])(implicit bf: CanBuildFrom[CherryTree[T], B, That]) =
    if (isDefaultCBF(bf)) concat(that.asInstanceOf[CherryTree[B]]).asInstanceOf[That] else super.++(that)
}

case object CherryNil extends CherryTree[Nothing] {
  override def head = throw new NoSuchElementException("head of empty CherryList")

  def lastCase = throw new UnsupportedOperationException("last of empty CherryList")

  override def tail = throw new UnsupportedOperationException("tail of empty CherryList")

  override def foreach[U](f: (Nothing) => U) = ()

  override def append[S >: Nothing](x: S): CherryTree[S] = CherrySingle(x)

  override def prepend[S >: Nothing](x: S): CherryTree[S] = CherrySingle(x)

  override def size = 0

  override def isEmpty = true
}

final case class CherrySingle[+T](x: T) extends CherryTree[T] {
  override def head = x

  def lastCase = x

  def tailCase = CherryNil

  def initCase = CherryNil

  override def foreach[U](f: T => U) = f(x)

  def append[S >: T](y: S) = CherryBranch(Node1(x), CherryNil, Node1(y))

  def prepend[S >: T](y: S) = CherryBranch(Node1(y), CherryNil, Node1(x))

  override def size = 1

  override def isEmpty = false
}

final case class CherryBranch[+T](left: Node[T], inner: CherryTree[Node2[T]], right: Node[T]) extends CherryTree[T] {
  override def head = left match {
    case Node1(x) => x
    case Node2(x, _) => x
  }

  def lastCase = left match {
    case Node1(x) => x
    case Node2(_, x) => x
  }

  def tailCase = left match {
    case Node1(_) => inner match {
      case CherryNil => right match {
        case Node1(x) => CherrySingle(x)
        case Node2(x, y) => CherryBranch(Node1(x), CherryNil, Node1(y))
      }
      case tree => CherryBranch(tree.head, tree.tail, right)
    }
    case Node2(_, x) => CherryBranch(Node1(x), inner, right)
  }

  def initCase = left match {
    case Node1(_) => inner match {
      case CherryNil => right match {
        case Node1(x) => CherrySingle(x)
        case Node2(x, y) => CherryBranch(Node1(x), CherryNil, Node1(y))
      }
      case tree => CherryBranch(tree.head, tree.tail, right)
    }
    case Node2(_, x) => CherryBranch(Node1(x), inner, right)
  }

  override def foreach[U](f: T => U) = {
    left.foreach(f)
    inner.foreach(_.foreach(f))
    right.foreach(f)
  }

  def append[S >: T](x: S) = right match {
    case Node1(y) => CherryBranch(left, inner, Node2(y, x))
    case n: Node2[S] => CherryBranch(left, inner.append(n), Node1(x))
  }

  def prepend[S >: T](x: S) = left match {
    case Node1(y) => CherryBranch(Node2(x, y), inner, right)
    case n: Node2[S] => CherryBranch(Node1(x), inner.append(n), right)
  }

  def applyCase[S >: T](n:Int) = {
    if (n < left.size) {
//      left(n)
    } else     if (n - left.size < 2 * inner.size) {
      inner(n - left.size)
    } else {
//      right(n - left.size - 2 * inner.size)
    }
    left.size + inner.size * 2 + right.size
  }

  override def size = left.size + inner.size * 2 + right.size

  override def isEmpty = false
}


object CherryTree extends SeqFactory[CherryTree] {

  private class CherryTreeBuilder[T]() extends mutable.Builder[T, CherryTree[T]] {
    private[this] var coll: CherryTree[T] = CherryNil

    def +=(elem: T): CherryTreeBuilder.this.type = {
      coll = coll.append(elem);
      this
    }

    def clear(): Unit = coll = CherryNil

    def result(): CherryTree[T] = coll
  }

  def concateThis[S](first: CherryTree[S], second: CherryTree[S]): CherryTree[S] = {
    var res: CherryTree[S] = first
    second.foreach(s => res = res.append(s))
    res
  }

  def concatePrependThis[S](first: CherryTree[S], second: CherryTree[S]): CherryTree[S] = second.foldRight(first)((s, res) => res.prepend(s))


  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, CherryTree[A]] =
    ReusableCBF.asInstanceOf[GenericCanBuildFrom[A]]

  def newBuilder[T]: mutable.Builder[T, CherryTree[T]] = new CherryTreeBuilder[T]

  sealed trait Node[+T] {
    def foreach[U](f: T => U): Unit

    def size: Int
  }

  final case class Node1[+T](x: T) extends Node[T] {
    override def foreach[U](f: (T) => U): Unit = f(x)

    def size = 1
  }

  final case class Node2[+T](x: T, y: T) extends Node[T] {
    def foreach[U](f: (T) => U): Unit = {
      f(x)
      f(y)
    }

    def size = 2
  }

}

