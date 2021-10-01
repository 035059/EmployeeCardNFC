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
import java.awt.EventQueue
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel

class questionAdd(title: String, ID: String, status: String) : JFrame() {

    init {
        createUI(title, ID, status)
    }

    private fun createUI(title: String, ID: String, status: String) {

        val catYellow = Color(240, 184, 35)
        val stdCols = arrayOf<Color>(catYellow, catYellow)

        val names = listOf("Yes", "No")
        val width = IntArray(4) { 375 }
        val height = intArrayOf(240, 240)

        val label = JLabel("ID not in Directory. Add to Directory?")
        label.font = Font("Helvetica", Font.BOLD, 40)

        class ButtonClickListener : ActionListener {
            override fun actionPerformed(e: ActionEvent) {
                when (e.actionCommand) {
                    "Yes" -> { println("adding person")
                        val windowTitle = "Test"
                        val window = addPerson(windowTitle, ID, status)
                        window.isAlwaysOnTop = true
                        window.isVisible = true
                        isVisible = false
                        dispose()
                    }
                    "No" -> {
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

        createLayout(label, buttons)

        setTitle(title)
        //defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }


    private fun createLayout(title: JLabel, labels: List<JButton>) {

        val gl = GroupLayout(contentPane)
        contentPane.layout = gl

        gl.autoCreateContainerGaps = true
        gl.autoCreateGaps = true

        gl.setHorizontalGroup(
            gl.createParallelGroup()
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(title)
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(labels[0])
                        .addComponent(labels[1])
                )
        )

        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(title)
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(labels[0])
                        .addComponent(labels[1])
                )
        )
        pack()
    }
}
