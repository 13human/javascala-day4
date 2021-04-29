package collections
//https://miro.com/app/board/o9J_lHxU6vE=/
object ListVsVector extends App {
  // Попробуем сравнить производительность List и Vector

  // Напишем функцию, которая ищет размер произвольной непустой коллекции
  def getSize(collection: Iterable[_]): Int = {
    assert(collection.nonEmpty)
    var currentIndex = 0
    val iterator = collection.iterator
    do {
      currentIndex += 1
      iterator.next()
    } while (iterator.hasNext)
    currentIndex
  }

  val N: Int = 1000

  //Чтобы усреднить время выполнения функции, напишем метод, который будет выполнять эту функцию N раз
  def repeatGetSizeNTimes(collection: Iterable[_]): Unit = {
    var currentIndex = 0
    while (currentIndex < N) {
      getSize(collection)
      currentIndex = currentIndex + 1
    }
  }

  /** Напишем тестовый фреймворк, который получит среднее время получения последнего элемента
   * для коллекций разной длины 100, 1000, 10000, 100000, 1000000
   */
  def testCollectionOfDifferentSizes(): Unit = {
    val sizes: List[Int] = 50 :: 100 :: 500 :: 1000 :: 5000 :: 10000 :: 50000 :: 100000 :: 500000 :: 1000000 :: Nil
    sizes.foreach { size =>
      val testList: Seq[Int] = List.empty[Int].padTo(size, 0)
      val testVector = Vector.empty[Int].padTo(size, 0)
      val t1: Long = System.currentTimeMillis()
      repeatGetSizeNTimes(testList)
      val t2: Long = System.currentTimeMillis()
      repeatGetSizeNTimes(testVector)
      val t3: Long = System.currentTimeMillis()
      println(s"$size\t${t2 - t1}\t${t3 - t2}")
    }
  }

  testCollectionOfDifferentSizes()
  // постройте график https://live.amcharts.com/new/edit/
  // сравните предположения и полученный результат











  /** Упражнение 1
   * Перепишите getSize используя родные методы Iterable
   */
  def getSizeNative(collection: Iterable[_]): Int = 0

  /** Упражнение 2
   * Перепишите repeatGetSizeNTimes используя Range
   */
  def repeatGetSizeRange(collection: Iterable[_]): Unit = {}

  /** Упражнение 3
   * Перепишите testCollectionOfDifferentSizes используя for comprehensions
   * Подсказка: размеры массивов это степени 10 от 2 до 6. Плюс дополнительный точки равный половинкам этих
   * Можно использовать Math.pow
   */
  def testCollectionOfDifferentSizesFC(): Unit = {
    for {
      power <- 2 to 6
      pow = Math.pow(10, power).toInt
      size <- List(pow / 2, pow)
    } yield {
      //  Упражнение 4. Лист и вектор нужных размеров создайте из Range
      val list: Seq[Int] = List().padTo(size, 0)
      val vector: Seq[Int] = Vector().padTo(size, 0)

      val t1: Long = System.currentTimeMillis()
      (1 to N).foreach(_ => getSizeNative(list))
      val t2: Long = System.currentTimeMillis()
      (1 to N).foreach(_ => getSizeNative(vector))
      val t3: Long = System.currentTimeMillis()
      println(s"$size\t${t2 - t1}\t${t3 - t2}")
    }
  }
}
