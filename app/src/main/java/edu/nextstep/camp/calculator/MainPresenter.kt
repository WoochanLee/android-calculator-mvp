package edu.nextstep.camp.calculator

import edu.nextstep.camp.calculator.domain.*

class MainPresenter(
    private val view: MainContract.View,
    private val calculator: Calculator,
    private var expression: Expression = Expression.EMPTY,
    history: History = History.EMPTY
) : MainContract.Presenter {

    private var _history: History = history

    override fun addToExpression(operand: Int) {
        expression += operand
        view.showExpression(expression.toString())
    }

    override fun addToExpression(operator: Operator) {
        expression += operator
        view.showExpression(expression.toString())
    }

    override fun removeLatest() {
        expression = expression.removeLast()
        view.showExpression(expression.toString())
    }

    override fun calculate() {
        val result = calculator.calculate(expression.toString())
        if (result == null) {
            view.showIncompleteExpressionToast()
            return
        }
        _history += Record(expression.toString(), result.toString())
        view.showHistory(_history)

        expression = Expression.EMPTY + result
        view.showExpression(expression.toString())
    }

    override fun changeDisplay(isHistoryDisplay: Boolean) {
        if (isHistoryDisplay) {
            view.showCalculateDisplay()
            return
        }

        view.showHistoryDisplay()
    }

}
