package com.example.lab5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5.databinding.CalcActivityBinding

class CalcActivity : AppCompatActivity() {
    private var objectType = 0
    private var area = 1
    private var electricity = 0
    private var laminate = 0
    private var plate = 0
    private var wallPaper = 0
    private var wallPaint = 0

    private lateinit var binding: CalcActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalcActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.areaSeek.progress = area

        binding.areaInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    val inputArea = s.toString().toInt()
                    if (inputArea in binding.areaSeek.min..binding.areaSeek.max) {
                        area = inputArea
                        binding.areaSeek.progress = area
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.areaSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    area = progress
                    binding.areaInput.setText(area.toString())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            objectType = when (checkedId) {
                binding.objectRadioButton3.id -> 6975
                binding.objectRadioButton4.id -> 6750
                else -> 0
            }
        }
        binding.radioGroup2.setOnCheckedChangeListener{ _, checkedId ->
            electricity = when (checkedId) {
                binding.electricityRadioButton1.id -> 900
                else -> 0
            }
        }
        binding.floorLaminate.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                laminate = 225
            }
        }
        binding.floorPlate.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                plate = 765
            }
        }
        binding.wallPaper.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                wallPaper = 315
            }
        }
        binding.wallPaint.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                wallPaint = 505
            }
        }

        binding.result.setOnClickListener{
            val result = calculate(objectType, area, electricity, laminate, plate, wallPaper, wallPaint)
            val resultIntent = Intent()
            resultIntent.putExtra("resultKey", result.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
    private fun calculate(objectType: Int, area: Int, electricity: Int, laminate: Int, plate: Int, wallPaper: Int, wallPaint: Int):Int {
        return objectType * area + electricity * area + laminate * area + plate * area + wallPaper * area + wallPaint * area
    }
}