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
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File

//this function loads the directory and creates a new row with the information of a new person, tying it to their decrypted card number
fun createPerson(fullName: String, empNum: String, ID: String, shift: String){
    val file = File("/home/pi/Desktop/Directory.csv") //loads file from directory
    val newRow = listOf(listOf(fullName,empNum,ID, shift)) //creates a list with tech's information
    csvWriter().writeAll(newRow, file, append = true) //puts the created list at the end of the directory
}

//This is the pop-up screen which prompts the tech to input their information to be tied to their card number
class addPerson(title: String, ID: String, status: String) : JFrame(){

    //creates the User Interface (UI)
    init {
        createUI(title, ID, status)
    }

    private fun createUI(title: String, ID: String, status: String) {
        //define headers and information to be displayed along with font sizes
        val header = JLabel("Please Fill In the Following Information")
        header.font = Font("Helvetica", Font.BOLD, 30)
        val label1 = JLabel("Full Name")
        label1.font = Font("Helvetica", Font.BOLD, 18)
        val label2 = JLabel("4-Digit Employee Number")
        label2.font = Font("Helvetica", Font.BOLD, 18)
        val label3 = JLabel("Shift Type")
        label3.font = Font("Helvetica", Font.BOLD, 18)

        //creates text boxes
        val text1 = JTextField()
        text1.font = Font("Helvetica", Font.PLAIN, 18)
        val text2 = JTextField()
        text2.font = Font("Helvetica", Font.PLAIN, 18)

        //creates combo box with options found in array below
        val shifts: Array<String> = arrayOf("", "Day 5:00 Start", "Day 5:30 Start", "Night", "Weekend", "Five Day")
        val combo1 = JComboBox(shifts)
        combo1.font = Font("Helvetica", Font.PLAIN, 18)

        //colours used for the submit and exit buttons
        val stdCols = arrayOf<Color>(Color.green, Color.red)

        //define button names and sizes
        val names = listOf("Submit", "Exit")
        val width = IntArray(4) { 120 }
        val height = intArrayOf(50, 50)

        //creates a button listener to perform these tasks when the buttons are pressed
        class ButtonClickListener : ActionListener {
            override fun actionPerformed(e: ActionEvent) { //does this when a button is pressed
                when (e.actionCommand) {
                    "Submit" -> { // if the "Submit" button is pressed
                        //get information from text boxes and combo button
                        val nameID: String = text1.text
                        val empID: String = text2.text
                        val shift: String = combo1.selectedItem.toString()
                        if ((nameID == "") or (empID == "") or (shift == "")){ //prompts to input all information if some parts are not filled out
                            val window = displayMessage("Please Input All Information")
                            window.isAlwaysOnTop = true
                            window.isVisible = true
                        }
                        else{ //if all fields are filled out, create a person in the directory and sign them in or out according to the original button press
                            createPerson(nameID,empID,ID,shift)
                            writeToExcel(ID, nameID, empID, status, shift)
                            isVisible = false //hide this form
                            dispose() //unload the form
                        }

                    }
                    "Exit" -> { //if the exit button is pressed, hide and unload the form
                        isVisible = false
                        dispose()
                    }
                    else -> println("IDK man, something's dicked")
                }
            }
        }

        //create the buttons and sets their style
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

        //calls the createLayout function with the labels, text boxes, and combo button defined above
        createLayout(header, label1, label2, label3, text1, text2, combo1, buttons)

        setTitle(title)
        //defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)

    }


    //sets the layout of the form, just play around with this and see what works best
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
