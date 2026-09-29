package com.example.advancedcalculator

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.advancedcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val engine = CalculatorEngine()
    private var currentExpression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        val buttons = mapOf(
            binding.btn0 to "0", binding.btn1 to "1", binding.btn2 to "2",
            binding.btn3 to "3", binding.btn4 to "4", binding.btn5 to "5",
            binding.btn6 to "6", binding.btn7 to "7", binding.btn8 to "8",
            binding.btn9 to "9", binding.btnDot to ".",
            binding.btnAdd to "+", binding.btnSub to "-",
            binding.btnMul to "×", binding.btnDiv to "÷",
            binding.btnLeftParen to "(", binding.btnRightParen to ")",
            binding.btnPi to "π", binding.btnPow to "^",
            binding.btnSin to "sin(", binding.btnCos to "cos(",
            binding.btnTan to "tan(", binding.btnLog to "log(",
            binding.btnLn to "ln(", binding.btnSqrt to "sqrt("
        )

        buttons.forEach { (btn, valStr) ->
            btn.setOnClickListener { appendToExpression(valStr) }
        }

        binding.btnClear.setOnClickListener {
            currentExpression = ""
            updateDisplay()
            binding.tvResult.text = "0"
        }

        binding.btnEqual.setOnClickListener {
            try {
                val result = engine.calculate(currentExpression)
                binding.tvResult.text = if (result % 1.0 == 0.0) {
                    result.toLong().toString()
                } else {
                    result.toString()
                }
            } catch (e: Exception) {
                binding.tvResult.text = "خطأ"
            }
        }
    }

    private function appendToExpression(str: String) {
        currentExpression += str
        updateDisplay()
    }

    private fun updateDisplay() {
        binding.tvExpression.text = currentExpression
    }
}