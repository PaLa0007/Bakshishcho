package com.example.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
private const val INITIAL_PEOPLE_COUNT = 1
class MainActivity : AppCompatActivity() {
    private lateinit var editTextBillAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var textViewTipPercentage: TextView
    private lateinit var seekBarPeopleCount: SeekBar
    private lateinit var textViewPeopleAmount: TextView
    private lateinit var textViewTipAmount: TextView
    private lateinit var textViewTotalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextBillAmount = findViewById(R.id.editTextBillAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        textViewTipPercentage = findViewById(R.id.textViewTipPercentage)
        seekBarPeopleCount = findViewById(R.id.seekBarPeopleCount)
        textViewPeopleAmount = findViewById(R.id.textViewPeopleAmount)
        textViewTipAmount = findViewById(R.id.textViewTipAmount)
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        textViewTipPercentage.text = "$INITIAL_TIP_PERCENT%"
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                textViewTipPercentage.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekBarPeopleCount.progress = INITIAL_PEOPLE_COUNT
        textViewPeopleAmount.text = "$INITIAL_PEOPLE_COUNT"
        seekBarPeopleCount.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                textViewPeopleAmount.text = "$progress"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        editTextBillAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG,"afterTextChanged $s")
                computeTipAndTotal()
            }
        })
    }

    private fun computeTipAndTotal() {
        if (editTextBillAmount.text.isEmpty()){
            textViewTipAmount.text = ""
            textViewTotalAmount.text = ""
            return
        }
        //Get the value of the bill and tip percent
        val billAmount = editTextBillAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val peopleAmount = seekBarPeopleCount.progress
        //Compute the tip and total appropriately from people
        val tipAmount = billAmount * tipPercent / 100
        val totalAmount = (billAmount + tipAmount) / peopleAmount
        //Update the UI
        textViewTipAmount.text = "%.2f".format(tipAmount)
        textViewTotalAmount.text = "%.2f".format(totalAmount)
    }
}