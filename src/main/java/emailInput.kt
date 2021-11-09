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
import java.awt.Color
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File


class emailInput(title: String) : JFrame(){

    init {
        createUI(title)
    }

    private fun createUI(title: String) {
        val header = JLabel("Please Fill In the Following Information")
        header.font = Font("Helvetica", Font.BOLD, 30)
        val label1 = JLabel("Full Name")
        label1.font = Font("Helvetica", Font.BOLD, 18)
        val label2 = JLabel("4-Digit Employee Number")
        label2.font = Font("Helvetica", Font.BOLD, 18)
        val label3 = JLabel("Sign-In or Sign-Out")
        label3.font = Font("Helvetica", Font.BOLD, 18)
        val label4 = JLabel("Supervisor")
        label4.font = Font("Helvetica", Font.BOLD, 18)

        val text1 = JTextField()
        text1.font = Font("Helvetica", Font.PLAIN, 18)
        val text2 = JTextField()
        text2.font = Font("Helvetica", Font.PLAIN, 18)

        val radio1 = JRadioButton("In")
        radio1.font = Font("Helvetica", Font.PLAIN, 16)
        val radio2 = JRadioButton("Out")
        radio2.font = Font("Helvetica", Font.PLAIN, 16)

        val bg = ButtonGroup()
        bg.add(radio1)
        bg.add(radio2)

        val supervisors: Array<String> = arrayOf("", "Andrew Sibbald", "Bob Brkovich", "Bruce Hall", "Dan Pitre", "Guirgues Hanna", "Laith Khalil", "Mark Fratelli", "Mathieu Guimont", "Steve Linhardt")
        val supervisorEmails: Array<String> = arrayOf("", "ASibbald@toromont.com", "BBrkovich@toromont.com", "BHall@toromont.com", "DPitre@toromont.com", "GHanna@toromont.com", "LKhalil@toromont.com", "MFratelli@toromont.com", "MGuimont@toromont.com", "SLinhardt@toromont.com")
        val combo1 = JComboBox(supervisors)
        combo1.font = Font("Helvetica", Font.PLAIN, 16)

        val stdCols = arrayOf<Color>(Color.green, Color.red)

        val names = listOf("Submit", "Exit")
        val width = IntArray(4) { 120 }
        val height = intArrayOf(50, 50)

        class ButtonClickListener : ActionListener {
            override fun actionPerformed(e: ActionEvent) {
                when (e.actionCommand) {
                    "Submit" -> {
                        var nameID: String = text1.text
                        var empID: String = text2.text
                        var sup: String = combo1.selectedItem.toString()
                        var supEmail: String = ""
                        for (i in supervisors.indices){
                            if (sup == supervisors[i]) {supEmail = supervisorEmails[i]}
                        }
                        if ((nameID == "") or (empID == "") or (sup == "") or ((!radio1.isSelected) and (!radio2.isSelected))){
                            val window = displayMessage("Please Input All Information")
                            window.isAlwaysOnTop = true
                            window.isVisible = true
                        }
                        else{
                            var r =
                                if (radio1.isSelected){ "In" }
                                else if (radio2.isSelected){ "Out" }
                                else{ "lets not get here eh?" }

                            val shift = getShift(empID)
                            if (shift == "no") {
                                isVisible = false
                                dispose()
                            } else {
                                writeToExcel("No ID", nameID, empID, r, shift)
                                emailSupervisor(nameID, empID, r, supEmail)
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
                font = Font("Helvetica", Font.BOLD, 30)
            }
        }

        createLayout(header, label1, label2, label3, label4, text1, text2, radio1, radio2, combo1, buttons)

        setTitle(title)
        //defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)

    }



    private fun createLayout(header: JLabel, label1: JLabel, label2: JLabel, label3: JLabel, label4: JLabel, text1: JTextField, text2: JTextField, radio1: JRadioButton, radio2: JRadioButton, combo1: JComboBox<String>, buttons: List<JButton>) {

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
                        .addComponent(radio1)
                        .addComponent(radio2)
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(label4)
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
                        .addComponent(radio1)
                        .addComponent(radio2)
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(label4)
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
