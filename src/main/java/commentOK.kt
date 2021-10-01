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
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel

class commentOK (title: String): JFrame(){

    init {
        createUI(title)
    }

    private fun createUI(title: String) {

        val button = JButton().apply{
            text = "OK"
            minimumSize = Dimension(120, 60)
            isOpaque = true
            isResizable = true
            actionCommand = "OK"
            addActionListener(ButtonClickListener())
            font = Font("Helvetica", Font.BOLD, 40)
        }

        val label = JLabel(title)
        label.font = Font("Helvetica", Font.PLAIN, 24)

        createLayout(button, label)

        setTitle(title)
        //defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }

    private inner class ButtonClickListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            when (e.actionCommand) {
                "OK" -> {
                    isVisible = false
                    dispose()
                }
                else -> println("IDK man, something's dicked")
            }
        }
    }

    private fun createLayout(button: JButton, label: JLabel) {

        val gl = GroupLayout(contentPane)
        contentPane.layout = gl

        gl.autoCreateContainerGaps = true
        gl.autoCreateGaps = true


        gl.setHorizontalGroup(
            gl.createParallelGroup()
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(label)
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(button)
                )
        )

        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(label)
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(button)
                )
        )
        pack()
    }
}