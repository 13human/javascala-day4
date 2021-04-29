package pattern_matching
import pattern_matching.ChargesToPays.ChargeType.ChargeType
import pattern_matching.ChargesToPays.{BalanceFinancialAccount, CustomerAgreement}

object ChargesToPays {
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
  case class BalanceFinancialAccount(openingBalance: BigDecimal, currentBill: BigDecimal, penalty: BigDecimal) extends FinancialAccount {
    override def balances: Map[ChargeType, BigDecimal] = ???

    override def toCharges: Seq[Charge] = Seq()
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
    def pay: Paid = {
      Paid(tpe, amount)
    }
  }


  // Оплачено
  case class Paid(tpe: ChargeType.ChargeType, amount: BigDecimal)


  // Создадим case class CustomerAgreement c полями number: String и financialAccounts - коллекция FinancialAccount
  case class CustomerAgreement(number: String, financialAccounts: Seq[FinancialAccount]) {
    // 3. Задайте фиксированные порядки оплаты
    def payOrders = ???
    // 4. Напишите функцию распределений платежа
    def splitAmount(amount: BigDecimal) = {
      // 5. Необходимо собрать начисления со всех ФЛС в виде Charge
      val allCharges: Seq[Charge] = ???
      // 6. Сгруппировать их по типам ничислений
      val groupedCharges: Seq[(ChargeType.ChargeType, Seq[Charge])] = ???
      // 7. Отсортировать их согласно заданным порядкам оплаты
      val sortedGroups: Seq[(ChargeType.ChargeType, Seq[Charge])] = ???
      // 8. EXTRA LEVEL используя foldLeft и  splitAccumulator
      // распределить платеж

      val splittedCharges: SplitAccumulator = ???
      splittedCharges
    }
  }

  // 9. Добавьте метод тотал
  case class SplitAccumulator(leftAmount: BigDecimal, pays: Seq[Paid])
}

object TestSplitting extends App {
  val customerAgreement1 = CustomerAgreement(
    number = "test1",
    financialAccounts = Seq(
      BalanceFinancialAccount(openingBalance = 100, currentBill = 500, penalty = 10)
    )
  )
  val result1 = customerAgreement1.splitAmount(210)
  println(result1)
/*  assert(210 == result1.pays.map(_.amount).sum + result1.leftAmount, "Какие деньги?")
  // этот тест должен сломаться
  val customerAgreement2 = CustomerAgreement(
    number = "test2",
    financialAccounts = Seq(
      BalanceFinancialAccount(openingBalance = 100),
      BalanceFinancialAccount(openingBalance = 100),
      BalanceFinancialAccount(openingBalance = 100)
    )
  )
  customerAgreement2.splitAmount(100)
   assert(210 == result1.total, "Какие деньги?")*/
}
