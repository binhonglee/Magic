package binhong.magic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.io.*

class MainActivity : AppCompatActivity()
{
    internal val filename: String = "/saveFile.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById(R.id.editText) as EditText
        val saveBtn = findViewById(R.id.saveBtn) as Button
        val cancelBtn = findViewById(R.id.cancelBtn) as Button
        reload()

        saveBtn.setOnClickListener(View.OnClickListener {
            save(editText.text.toString())
        })
        cancelBtn.setOnClickListener(View.OnClickListener {
            editText.text.clear()
            editText.append(reload())
        })
    }

    fun save(input: String)
    {
        try
        {
            val outputFile = File(Environment.getExternalStorageDirectory().absolutePath + filename)
            outputFile.mkdirs()

            val fos = FileOutputStream(outputFile)
            fos.write(input.toByteArray())
            fos.close()

            println("File saved")
            val file = File(filename)
            println(file.exists())
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
    }

    fun reload(): String
    {
        val file = File(filename)
        val text = StringBuilder()

        try
        {
            val br = BufferedReader(FileReader(file))
            var line: String = br.readLine()

            while (line != null) {
                text.append(line)
                text.append('\n')
                line = br.readLine()
            }

            br.close()
        }
        catch (e: Exception)
        {
            println("File not found")
            return ""
        }

        return text.toString()
    }
}
