/*
 * MIT License
 *
 * Copyright (c) 2021 Owen Phillips, Allin Demopolis
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

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.*

fun getShift(empNum: String): String {
    val file: File = File("src/main/java/Directory.csv") //load directory
    val rows: List<List<String>> = csvReader().readAll(file)
    for (i in 0 until (rows.size)) { //go through each row and compare ID numbers
        if (rows[i][1] == empNum) {
            return rows[i][3]
            break //if there is a match, break
        }
    }
    val window = commentOK("Employee not in Directory, Please Alert P&Q")
    window.isAlwaysOnTop = true
    window.isVisible = true
    return "no"
}

    class supervisorInfoFill(title: String) : JFrame() {

        init {
            createUI(title)
        }

        private fun createUI(title: String) {
            val header = JLabel("Please Fill In the Following Information")
            header.font = Font("Helvetica", Font.BOLD, 24)

            val label1 = JLabel("Employee Name")
            label1.font = Font("Helvetica", Font.PLAIN, 20)
            val label2 = JLabel("Employee Number")
            label2.font = Font("Helvetica", Font.PLAIN, 20)
            val label3 = JLabel("Time in form hh:mm:ss (24 Hour Clock)")
            label3.font = Font("Helvetica", Font.PLAIN, 20)
            val label4 = JLabel("Date in form yyyy-mm-dd")
            label4.font = Font("Helvetica", Font.PLAIN, 20)
            val label5 = JLabel("Sign-In or Sign-Out")
            label5.font = Font("Helvetica", Font.PLAIN, 20)

            val text1 = JTextField()
            text1.font = Font("Helvetica", Font.PLAIN, 20)
            val text2 = JTextField()
            text2.font = Font("Helvetica", Font.PLAIN, 20)
            val text3 = JTextField()
            text3.font = Font("Helvetica", Font.PLAIN, 20)
            val text4 = JTextField()
            text4.font = Font("Helvetica", Font.PLAIN, 20)

            val radio1 = JRadioButton("In")
            radio1.font = Font("Helvetica", Font.PLAIN, 20)
            val radio2 = JRadioButton("Out")
            radio2.font = Font("Helvetica", Font.PLAIN, 20)

            val bg = ButtonGroup()
            bg.add(radio1)
            bg.add(radio2)

            val stdCols = arrayOf<Color>(Color.green, Color.red)

            val names = listOf("Submit", "Exit")
            val width = IntArray(4) { 120 }
            val height = intArrayOf(60, 60)

            class ButtonClickListener : ActionListener {
                override fun actionPerformed(e: ActionEvent) {
                    when (e.actionCommand) {
                        "Submit" -> {
                            var t1: String = text1.text
                            var t2: String = text2.text
                            var t3: String = text3.text
                            var t4: String = text4.text
                            if ((t1 == "") or (t2 == "") or (t3 == "") or (t4 == "") or ((!radio1.isSelected) and (!radio2.isSelected))) {
                                val window = commentOK("Please Input All Information")
                                window.isAlwaysOnTop = true
                                window.isVisible = true
                            } else {
                                var r =
                                    if (radio1.isSelected) {
                                        "In"
                                    } else if (radio2.isSelected) {
                                        "Out"
                                    } else {
                                        "lets not get here eh?"
                                    }

                                val shift = getShift(t2)
                                if (shift == "no") {
                                    isVisible = false
                                    dispose()
                                } else {
                                    writeToExcelSupervisor("No ID", t1, t2, r, shift, t3, t4)

                                    isVisible = false
                                    dispose()
                                }
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

            createLayout(
                header,
                label1,
                label2,
                label3,
                label4,
                label5,
                text1,
                text2,
                text3,
                text4,
                radio1,
                radio2,
                buttons
            )

            setTitle(title)
            //defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setLocationRelativeTo(null)

        }


        private fun createLayout(
            header: JLabel,
            label1: JLabel,
            label2: JLabel,
            label3: JLabel,
            label4: JLabel,
            label5: JLabel,
            text1: JTextField,
            text2: JTextField,
            text3: JTextField,
            text4: JTextField,
            radio1: JRadioButton,
            radio2: JRadioButton,
            buttons: List<JButton>
        ) {

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
                            .addComponent(text3)
                    )
                    .addGroup(
                        gl.createParallelGroup()
                            .addComponent(label4)
                            .addComponent(text4)
                    )
                    .addGroup(
                        gl.createParallelGroup()
                            .addComponent(label5)
                            .addComponent(radio1)
                            .addComponent(radio2)
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
                            .addComponent(text3)
                    )
                    .addGroup(
                        gl.createSequentialGroup()
                            .addComponent(label4)
                            .addComponent(text4)
                    )
                    .addGroup(
                        gl.createSequentialGroup()
                            .addComponent(label5)
                            .addComponent(radio1)
                            .addComponent(radio2)
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
