/*
 * MIT License
 *
 * Copyright (c) 2021 Allin Demopolis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File

fun createPerson(fullName: String, empNum: String, ID: String, shift: String){
    val file = File("src/main/java/Directory.csv")
    val newRow = listOf(listOf(fullName,empNum,ID, shift))
    csvWriter().writeAll(newRow, file, append = true)
}


class addPerson(title: String, ID: String, status: String) : JFrame(){

    init {
        createUI(title, ID, status)
    }

    private fun createUI(title: String, ID: String, status: String) {
        val header = JLabel("Please Fill In the Following Information")
        header.font = Font("Helvetica", Font.BOLD, 40)
        val label1 = JLabel("Full Name")
        label1.font = Font("Helvetica", Font.BOLD, 32)
        val label2 = JLabel("Employee Number")
        label2.font = Font("Helvetica", Font.BOLD, 32)
        val label3 = JLabel("Shift Type")
        label3.font = Font("Helvetica", Font.BOLD, 32)

        val text1 = JTextField()
        text1.font = Font("Helvetica", Font.PLAIN, 24)
        val text2 = JTextField()
        text2.font = Font("Helvetica", Font.PLAIN, 24)

        val shifts: Array<String> = arrayOf("", "Day", "Night", "Weekend", "Five Day")
        val combo1 = JComboBox(shifts)
        combo1.font = Font("Helvetica", Font.PLAIN, 24)

        val stdCols = arrayOf<Color>(Color.green, Color.red)

        val names = listOf("Submit", "Exit")
        val width = IntArray(4) { 120 }
        val height = intArrayOf(60, 60)

        class ButtonClickListener : ActionListener {
            override fun actionPerformed(e: ActionEvent) {
                when (e.actionCommand) {
                    "Submit" -> {
                        val nameID: String = text1.text
                        val empID: String = text2.text
                        val shift: String = combo1.selectedItem.toString()
                        if ((nameID == "") or (empID == "") or (shift == "")){
                            val window = commentOK("Please Input All Information")
                            window.isAlwaysOnTop = true
                            window.isVisible = true
                        }
                        else{
                            createPerson(nameID,empID,ID,shift)
                            writeToExcel(ID, nameID, empID, status, shift)
                            isVisible = false
                            dispose()
                        }

                    }
                    "Exit" -> {
                        isVisible = false
                        dispose()
                    }
                    else -> println("IDK man, something's dicked")
                }
            }
        }

        val buttons = stdCols.mapIndexed { index, color ->
            JButton().apply {
                text = names[index]
                minimumSize = Dimension(width[index], height[index])
                background = color
                isOpaque = true
                isResizable = true
                actionCommand = names[index]
                addActionListener(ButtonClickListener())
                font = Font("Helvetica", Font.BOLD, 40)
            }
        }

        createLayout(header, label1, label2, label3, text1, text2, combo1, buttons)

        setTitle(title)
        //defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)

    }



    private fun createLayout(header: JLabel, label1: JLabel, label2: JLabel, label3: JLabel, text1: JTextField, text2: JTextField, combo1: JComboBox<String>, buttons: List<JButton>) {

        val gl = GroupLayout(contentPane)
        contentPane.layout = gl

        gl.autoCreateContainerGaps = true
        gl.autoCreateGaps = true

        gl.setHorizontalGroup(
            gl.createParallelGroup()
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(header)
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(label1)
                        .addComponent(text1)
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(label2)
                        .addComponent(text2)
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(label3)
                        .addComponent(combo1)
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(buttons[0])
                        .addComponent(buttons[1])
                )
        )
        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(header)
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(label1)
                        .addComponent(text1)
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(label2)
                        .addComponent(text2)
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(label3)
                        .addComponent(combo1)
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(buttons[0])
                        .addComponent(buttons[1])
                )
        )
        pack()

    }
}
