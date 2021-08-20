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

data class TapEvent(val csvLine: String) {
    //    "Time","Activity","Panel","Panel Location","Mod","Door","Door Location","PIN","Card #","Last Name","First Name"
    var time: Int = 0
    var activity: String
        get() = ""
        set(value) = TODO()
    var panel: String
        get() = ""
        set(value) = TODO()
    var panelLocation: String
        get() = ""
        set(value) = TODO()
    var mod: String
        get() = ""
        set(value) = TODO()
    var door: String
        get() = ""
        set(value) = TODO()
    var doorLocation: String
        get() = ""
        set(value) = TODO()
    var pin: String
        get() = ""
        set(value) = TODO()
    var cardNum: String
        get() = ""
        set(value) = TODO()
    var lastName: String
        get() = ""
        set(value) = TODO()
    var firstName: String
        get() = ""
        set(value) = TODO()

}
