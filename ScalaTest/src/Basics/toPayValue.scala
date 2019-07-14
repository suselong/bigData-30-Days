package Basics

object toPayValue {
  var money = 1000

  /**
    * 吃一次饭
    */
  def eat(): Unit = {
    money -= 50
    println("吃了一次饭")
  }

  /**
    * 余额
    *
    * @return
    */
  def balance(): Int = {
    eat()
    money
  }

  /**
    * 打印
    *
    * @param x 余额
    */
  def printMoney(x: Int): Unit = {
    for (_ <- 1 to 5) {
      println(s"你的余额：$x")
    }
  }

  /**
    * 主方法
    */
  def main(args: Array[String]): Unit = {
    printMoney(balance())
  }
}
