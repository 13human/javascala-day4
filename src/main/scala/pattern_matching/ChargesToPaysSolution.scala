/*
package pattern_matching

import pattern_matching.ChargesToPaysSolution.ChargeType.ChargeType
import pattern_matching.ChargesToPaysSolution.{BalanceFinancialAccount, CustomerAgreement}

import scala.math.BigDecimal.RoundingMode

object ChargesToPaysSolution {
  /** Повторим упрощенно пример распределения платежа
   * Пусть у нас будет Договор с клиентом CustomerAgreement c номером и списком финансовых лицевых счетов.
   * Каждый финансовый лицевой счет обладает списком начислений
   * Существует два типа ФЛС:
   * балансовый - с типами начислений НальноеСальдо, ТекущееНачисление, Пени
   * кредитный - с типами начислений Неустойка, КОплатеВТекущемМесяце, Остаток
   * Необходимо, имея заранее заданный порядок оплаты начислений и договор, распределить сумму платежа по всем ФЛС и начислениям
   * */

  object ChargeType extends Enumeration {
    type ChargeType = Value
    val НальноеСальдо, ТекущееНачисление, Пени, Неустойка, КОплатеВТекущемМесяце, Остаток = Value
  }

  trait FinancialAccount {
    def balances: Map[ChargeType.ChargeType, BigDecimal]

    def toCharges: Seq[Charge] // можно реализовать и здесь и в наследниках
  }

  // 1. Создадим case class BalanceFinancialAccount с полями openingBalance, currentBill, penalty
  /** Балансовый ФЛС
   * @param openingBalance НальноеСальдо
   * @param currentBill ТекущееНачисление
   * @param penalty Пени
   */
  case class BalanceFinancialAccount(openingBalance: BigDecimal = 0, currentBill: BigDecimal = 0, penalty: BigDecimal = 0) extends FinancialAccount {
    override def balances: Map[ChargeType, BigDecimal] = Map(
      ChargeType.НальноеСальдо -> openingBalance
    )

    override def toCharges: Seq[Charge] = balances.map { case (k,v) => Charge(k, v)}.toSeq
  }

  // 2. Создайте case class CreditFinancialAccount c полями overdue, toPay, leftAmount
  /** Кредитный ФЛС
   * @param overdue Неустойка
   * @param toPay КОплатеВТекущемМесяце
   * @param leftAmount Остаток
   */
  ???

  // Начисление
  case class Charge(tpe: ChargeType.ChargeType, amount: BigDecimal) {
    // Может надо доработать?
    def pay(percent: BigDecimal = 1): Paid = {
      Paid(tpe, (amount * percent).setScale(2, RoundingMode.HALF_DOWN))
    }
  }

  // Оплачено
  case class Paid(tpe: ChargeType.ChargeType, amount: BigDecimal)



  // Создадим case class CustomerAgreement c полями number: String и financialAccounts - коллекция FinancialAccount
  case class CustomerAgreement(number: String, financialAccounts: Seq[FinancialAccount]) {
    // 3. Задайте фиксированные порядки оплаты
    def payOrders = Map(ChargeType.НальноеСальдо -> 1)
    // 4. Напишите функцию распределений платежа
    def splitAmount(amount: BigDecimal) = {
      // 5. Необходимо собрать начисления со всех ФЛС в виде Charge
      val allCharges: Seq[Charge] = financialAccounts.flatMap(_.toCharges)
      // 6. Сгруппировать их по типам ничислений
      val groupedCharges: Map[ChargeType.ChargeType, Seq[Charge]] = allCharges.groupBy(_.tpe)
      // 7. Отсортировать их согласно заданным порядкам оплаты
      val sortedGroups: Seq[(ChargeType.ChargeType, Seq[Charge])] = groupedCharges.toSeq.sortBy( kv => payOrders.getOrElse(kv._1, 0))
      // 8. EXTRA LEVEL используя foldLeft и  splitAccumulator
      // распределить платеж
      case class SplitAccumulator(leftAmount: BigDecimal, pays: Seq[Paid])
      val splittedCharges: SplitAccumulator = sortedGroups.map(_._2).foldLeft(SplitAccumulator(amount, Seq())) { (acc, el) =>
        val chargesAmount = el.map(_.amount).sum
        if (acc.leftAmount > chargesAmount) {
          SplitAccumulator(acc.leftAmount - chargesAmount, acc.pays ++ el.map(_.pay()))
        } else {
          val percent = acc.leftAmount / chargesAmount
          SplitAccumulator(0, acc.pays ++ el.map(_.pay(percent)))
        }
      }
      splittedCharges
    }
  }

}

object TestSplittingSolution extends App {
  val customerAgreement1 = CustomerAgreement(
    number = "test1",
    financialAccounts = Seq(
      BalanceFinancialAccount(openingBalance = 100, currentBill = 500, penalty = 10)
    )
  )
  val result1 = customerAgreement1.splitAmount(210)
  println(result1)

  // этот тест должен сломаться
  val customerAgreement2 = CustomerAgreement(
    number = "test2",
    financialAccounts = Seq(
      BalanceFinancialAccount(openingBalance = 100),
      BalanceFinancialAccount(openingBalance = 100),
      BalanceFinancialAccount(openingBalance = 100)
    )
  )
  val result2 = customerAgreement2.splitAmount(100)
  println(result2)
  assert(100 == result2.pays.map(_.amount).sum + result2.leftAmount)
}
*/
