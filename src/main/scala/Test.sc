def l1 = 1::2::3::4::5::6::7::8::9::10::Nil
def l2 = Seq(1,2,3,4,5,6,7,8,9,10)
def l3 = (1 to 10).toList
def l4 = List(1,2,3,4,5,6,7,8,9,10)

println(l1)
println(l2)
println(l3)

def repeat(n: Int, f: () => Seq[Int]) = {
  val t1 = System.currentTimeMillis()
  (1 to n).foreach(_ => f())
  println(System.currentTimeMillis() - t1)
}
val n = 10000000
repeat(n, () => l1)
repeat(n, () => l2)
repeat(n, () => l3)
repeat(n, () => l4)
List.apply()