package Basics

object ManyParm {
  def sum(a: Int*): Int = {
    var sum = 0
    for (v <- a) {
      sum += v
    }
    sum
  }

  //Any*可以传入任意的类型
  def setName(args:Any*):Unit={
    for(arg <- args){
      println(arg)
    }
  }

  def main(args: Array[String]): Unit = {
    println(sum(1,2,3,4))
    println(sum(1,2,3,4,5,6))
    setName("along","aera",1,2)
    setName("123",1)
  }
}
