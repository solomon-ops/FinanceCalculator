package com.example.financecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get references to UI elements
        val loanAmountEditText: EditText = findViewById(R.id.loanAmountEditText)
        val interestRateEditText: EditText = findViewById(R.id.interestRateEditText)
        val loanTermEditText: EditText = findViewById(R.id.loanTermEditText)
        val calculateButton: Button = findViewById(R.id.calculateButton)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        // Set up the calculate button click listener
        calculateButton.setOnClickListener(View.OnClickListener {
            val loanAmount = loanAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
            val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
            val loanTerm = loanTermEditText.text.toString().toDoubleOrNull() ?: 0.0

            if (loanAmount > 0 && interestRate > 0 && loanTerm > 0) {
                // Calculate the monthly payment
                val monthlyPayment = calculateMonthlyPayment(loanAmount, interestRate, loanTerm)

                // Display the result
                resultTextView.text = "Monthly Payment: ₹${"%.2f".format(monthlyPayment)}"
            } else {
                resultTextView.text = "Please fill in all fields."
            }
        })
    }

    // Function to calculate the monthly loan payment
    private fun calculateMonthlyPayment(loanAmount: Double, interestRate: Double, loanTerm: Double): Double {
        // Convert annual interest rate to monthly rate
        val monthlyInterestRate = interestRate / 100 / 12
        // Convert loan term to months
        val numberOfMonths = loanTerm * 12

        // Formula for monthly payment: M = P [ r(1+r)^n ] / [ (1+r)^n – 1 ]
        if (monthlyInterestRate > 0) {
            return loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfMonths)) /
                    (Math.pow(1 + monthlyInterestRate, numberOfMonths) - 1)
        } else {
            return loanAmount / numberOfMonths // No interest case
        }
    }
}