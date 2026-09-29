package com.example.advancedcalculator

import kotlin.math.*

class CalculatorEngine {

    fun calculate(expression: String): Double {
        val sanitized = expression
            .replace("×", "*")
            .replace("÷", "/")
            .replace("π", PI.toString())
            .replace("e", E.toString())

        return evaluateExpression(sanitized)
    }

    private fun evaluateExpression(expr: String): Double {
        val tokens = tokenize(expr)
        val rpn = toRPN(tokens)
        return evalRPN(rpn)
    }

    private fun tokenize(s: String): List<String> {
        val tokens = mutableListOf<String>()
        var i = 0
        while (i < s.length) {
            val c = s[i]
            if (c.isWhitespace()) {
                i++
                continue
            }
            if (c.isDigit() || c == '.') {
                val sb = StringBuilder()
                while (i < s.length && (s[i].isDigit() || s[i] == '.')) {
                    sb.append(s[i])
                    i++
                }
                tokens.add(sb.toString())
                continue
            }
            if (c.isLetter()) {
                val sb = StringBuilder()
                while (i < s.length && s[i].isLetter()) {
                    sb.append(s[i])
                    i++
                }
                tokens.add(sb.toString())
                continue
            }
            tokens.add(c.toString())
            i++
        }
        return tokens
    }

    private fun toRPN(tokens: List<String>): List<String> {
        val out = mutableListOf<String>()
        val ops = ArrayDeque<String>()
        val prec = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2, "^" to 3)

        for (t in tokens) {
            when {
                t.toDoubleOrNull() != null || t == "π" || t == "e" -> out.add(t)
                t.isLetter() -> ops.addLast(t)
                prec.containsKey(t) -> {
                    while (ops.isNotEmpty() && prec.containsKey(ops.last()) && prec[ops.last()]!! >= prec[t]!!) {
                        out.add(ops.removeLast())
                    }
                    ops.addLast(t)
                }
                t == "(" -> ops.addLast(t)
                t == ")" -> {
                    while (ops.isNotEmpty() && ops.last() != "(") {
                        out.add(ops.removeLast())
                    }
                    if (ops.isNotEmpty() && ops.last() == "(") ops.removeLast()
                    if (ops.isNotEmpty() && ops.last().isLetter()) {
                        out.add(ops.removeLast())
                    }
                }
            }
        }
        while (ops.isNotEmpty()) {
            out.add(ops.removeLast())
        }
        return out
    }

    private fun evalRPN(rpn: List<String>): Double {
        val st = ArrayDeque<Double>()
        for (t in rpn) {
            when {
                t.toDoubleOrNull() != null -> st.addLast(t.toDouble())
                t == "π" -> st.addLast(PI)
                t == "e" -> st.addLast(E)
                t == "+" -> { val b = st.removeLast(); val a = st.removeLast(); st.addLast(a + b) }
                t == "-" -> { val b = st.removeLast(); val a = st.removeLast(); st.addLast(a - b) }
                t == "*" -> { val b = st.removeLast(); val a = st.removeLast(); st.addLast(a * b) }
                t == "/" -> { val b = st.removeLast(); val a = st.removeLast(); st.addLast(a / b) }
                t == "^" -> { val b = st.removeLast(); val a = st.removeLast(); st.addLast(a.pow(b)) }
                t.isLetter() -> {
                    val a = st.removeLast()
                    val res = when (t) {
                        "sin" -> sin(Math.toRadians(a))
                        "cos" -> cos(Math.toRadians(a))
                        "tan" -> tan(Math.toRadians(a))
                        "log" -> log10(a)
                        "ln" -> ln(a)
                        "sqrt" -> sqrt(a)
                        else -> 0.0
                    }
                    st.addLast(res)
                }
            }
        }
        return if (st.isNotEmpty()) st.removeLast() else 0.0
    }
}