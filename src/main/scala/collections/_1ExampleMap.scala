package collections

import collections._1ExampleMap.{immutableMap, mutableMap}

import scala.collection.{immutable, mutable}

// Просто пример как можно ориентироваться
object _1ExampleMap {
  var rootMap: scala.collection.Map[_, _] = Map()
  rootMap = scala.collection.mutable.Map.empty[String, String]
  rootMap = scala.collection.immutable.Map.empty[String, String]

  val mutableMap: mutable.Map[String, String] = mutable.Map("key" -> "value")

  var immutableMap: immutable.Map[String, String] = immutable.Map("key" -> "value")

  // Допустим мы хотим добавить новое ключ-значение
  mutableMap.+=("key2" -> "value2")
  mutableMap.updated("key2", "value2")
  // immutable коллекции недоступна данная операция, если не хранить ее в переменной
  immutableMap.+=("key2" -> "value2")
  //immutableMap = immutableMap.updated("key2", "value2")

  var mutableSeq: mutable.Seq[_] = _
  var immutableSeq: immutable.Seq[_] = _
  var mutableSet: mutable.Set[_] = _
  var immutableSet: immutable.Set[_] = _
}
object MapTest extends App {
  println(s"there is updated mutableMap $mutableMap  ${mutableMap.getClass}")
  println(s"there is updated immutableMap $immutableMap  ${immutableMap.getClass}")
}