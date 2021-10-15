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
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JFrame

class RaspiAccess(title: String) : JFrame() {

    init {
        createUI(title)
    }

    private fun createUI(title: String) {

        val catYellow = Color(240, 184, 35)
        val stdCols = arrayOf<Color>(catYellow, catYellow, Color.green, Color.red)

        val names = listOf("No ID Card", "Supervisor Login", "IN", "OUT")
        val width = IntArray(4) { 385 }
        val height = intArrayOf(60, 60, 300, 300)

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

        createLayout(buttons)

        setTitle(title)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }

    private inner class ButtonClickListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            when (e.actionCommand) {
                "IN" -> CardDataIn()
                "OUT" -> CardDataOut()
                "Supervisor Login" -> {
                    val window = supervisorSignIn("Supervisor Sign-In")
                    window.isAlwaysOnTop = true
                    window.isVisible = true
                }
                "No ID Card" -> {
                    val window = emailInput("Input Information")
                    window.isAlwaysOnTop = true
                    window.isVisible = true
                }
                else -> println("IDK man, something's dicked")
            }
        }
    }

    private fun createLayout(labels: List<JButton>) {

        val gl = GroupLayout(contentPane)
        contentPane.layout = gl

        gl.autoCreateContainerGaps = true
        gl.autoCreateGaps = true


        gl.setHorizontalGroup(
            gl.createParallelGroup()
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(labels[0])
                        .addComponent(labels[1])
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(labels[2])
                        .addComponent(labels[3])
                )
        )

        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(labels[0])
                        .addComponent(labels[1])
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(labels[2])
                        .addComponent(labels[3])
                )
        )
        pack()
    }
}


fun createAndShowGUI() {

    val frame = RaspiAccess("User Access Window")
    frame.isVisible = true
}

