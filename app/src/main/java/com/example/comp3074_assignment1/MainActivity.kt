package com.example.comp3074_assignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comp3074_assignment1.ui.theme.COMP3074_Assignment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            COMP3074_Assignment1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeSc(
                        name = "Assignment1_Comp3074",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeSc(name: String, modifier: Modifier = Modifier) {

    var NumberOfHoursWorked by remember { mutableStateOf("") }
    var TaxRate by remember { mutableStateOf("") }
    var HourlyRate by remember { mutableStateOf("") }
    var payment by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize().padding(16.dp),) {
        Text(
            text = "Calculator",
        )

        OutlinedTextField(
            value = NumberOfHoursWorked,
            onValueChange = { newInput -> NumberOfHoursWorked = newInput },
            label = { Text("Number of hours worked") }
        )
        OutlinedTextField(
            value = TaxRate,
            onValueChange = { newInput -> TaxRate = newInput },
            label = { Text("Tax Rate") }
        )
        OutlinedTextField(
            value = HourlyRate,
            onValueChange = { newInput -> HourlyRate = newInput },
            label = { Text("Hourly Rate") }
        )

        Button(
            onClick = { resultText = calculate(NumberOfHoursWorked,TaxRate,HourlyRate,payment) },
        ) {
            Text("Calculate")
        }

        if (resultText == ""){
            Text("Result: ")
        }else{
            Text("Result: $resultText")
        }
    }

}

fun calculate(NumberOfHoursWorked: String, Tax_rate: String, HourlyRate: String,payment: String): String {
    val nOfHours = NumberOfHoursWorked.toDoubleOrNull() ?: 0.0
    val taxRate = Tax_rate.toDoubleOrNull()
    val hourlyRate = HourlyRate.toDoubleOrNull() ?: 0.0
    val tax_rate = taxRate ?: 0.0;


    var payment: Double
    var overTimePayment: Double

    if (nOfHours <= 40) {
        payment = nOfHours * hourlyRate
        overTimePayment = 0.0
    } else {
        payment = 40 * hourlyRate
        overTimePayment = (nOfHours - 40) * hourlyRate * 1.5
    }

    val totalPayment = payment + overTimePayment
    val tax = payment * (tax_rate / 100.0)

    val formatPay = "%.2f".format(payment)
    val formatOvertime = "%.2f".format(overTimePayment)
    val formatTax = "%.2f".format(tax)
    val formatNet = "%.2f".format(payment)

    return "Pay: $formatNet \nOverTime Pay: $formatOvertime \nTax: $formatTax \nTotal Pay (Net): ${formatNet}"
}
