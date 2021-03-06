import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

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


class supervisorSignIn(title: String): JFrame() {

    init {
        createUI(title)
    }

    private fun createUI(title: String) {
        val header = JLabel("Please Fill In the Supervisor Password")
        header.font = Font("Helvetica", Font.BOLD, 24)

        val text1 = JTextField()
        text1.font = Font("Helvetica", Font.PLAIN, 24)

        val stdCols = arrayOf<Color>(Color.green, Color.red)

        val names = listOf("Next", "Exit")
        val width = IntArray(4) { 120 }
        val height = intArrayOf(60, 60)

        class ButtonClickListener : ActionListener {
            override fun actionPerformed(e: ActionEvent) {
                when (e.actionCommand) {
                    "Next" -> {
                        var password: String = text1.text
                        if (password == "pass123"){

                            val window = supervisorInfoFill("Supervisor Info Fill")
                            window.isAlwaysOnTop = true
                            window.isVisible = true

                            isVisible = false
                            dispose()
                        }
                        else{
                            val window = displayMessage("Password Incorrect")
                            window.isAlwaysOnTop = true
                            window.isVisible = true
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

        createLayout(header, text1, buttons)

        setTitle(title)
        //defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)

    }



    private fun createLayout(header: JLabel, text1: JTextField, buttons: List<JButton>) {

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
                        .addComponent(text1)
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
                        .addComponent(text1)
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