package com.example.monthlyloanpaymentcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monthlyloanpaymentcalc.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener { calculate() }
    }

    private fun calculate() {

        //get loan amount 5
        val stringInloanAmount = binding.loanAmount.text.toString()
        val loan = stringInloanAmount.toDoubleOrNull()
        if (loan == null) {
            binding.monthlyPayment.text = ""
            return
        }
        //get interest rate 5
        val stringInterestRate = binding.loanInterest.text.toString()
        val iRate = stringInterestRate.toDoubleOrNull()
        if (iRate == null) {
            binding.monthlyPayment.text = ""
            return
        }

        //get loanPeriod 5
        val loanPeriod = when (binding.loanPeriod.checkedRadioButtonId) {
            R.id.one_year -> 12
            R.id.two_years -> 24
            R.id.three_years -> 36
            R.id.four_years -> 48
            else -> 60
        }
        //get downPayment 5
        val downPaymentPercent = when (binding.downpayment.checkedRadioButtonId) {
            R.id.five -> 5
            R.id.ten -> 10
            R.id.twenty_five -> 25
            R.id.fifty -> 50
            else -> 75
        }
        //compute monthlyPayment 6
        var loanAmount = loan - (downPaymentPercent/100 * loan)

        var monthlyPayment = loanAmount * ((iRate/100) * Math.pow((1 + iRate/100).toDouble(),loanPeriod.toDouble()))/Math.pow((1+iRate/100).toDouble(),(loanPeriod-1).toDouble())

        //display and bind 4
        val formattedTip = NumberFormat.getCurrencyInstance().format(monthlyPayment)
        binding.monthlyPayment.text = getString(R.string.monthly, formattedTip)
    }
}