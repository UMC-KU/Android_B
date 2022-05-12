package com.example.app24_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.app24_sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myDBHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getAllRecord()
    }

    private fun getAllRecord(){
        myDBHelper.getAllRecord()
    }

    private fun clearEditText(){
        binding.apply {
            pIdEdit.text.clear()
            pNameEdit.text.clear()
            pQuantityEdit.text.clear()
        }
    }

    private fun init() {
        myDBHelper = MyDBHelper(this)
        binding.apply {
            insertbtn.setOnClickListener {
                val name = pNameEdit.text.toString()
                val quantity = pQuantityEdit.text.toString().toInt()
                val product = Product(0, name, quantity)
                val result = myDBHelper.insertProduct(product)
                if (result) {
                    getAllRecord()
                    Toast.makeText(this@MainActivity, "Data INSERT SUCCESS", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Data INSERT FAILED", Toast.LENGTH_SHORT).show()
                }
                clearEditText()

            }
            findbtn.setOnClickListener {
                val name = pNameEdit.text.toString()
                val result = myDBHelper.findProduct(name)
                if (result) {
                    Toast.makeText(this@MainActivity, "Record Found", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "NO MATCH", Toast.LENGTH_SHORT).show()
                }
            }
            deletebtn.setOnClickListener {

            }
            updatebtn.setOnClickListener {

            }

        }

    }
}