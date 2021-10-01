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

import java.awt.EventQueue
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

fun main() {
/*
    //TODO modify to get data from multiple files
*/

    val windowTitle = "Test"
    val window = RaspiAccess(windowTitle)
    window.isAlwaysOnTop = true
    EventQueue.invokeLater(::createAndShowGUI)


    val startDayTime = LocalTime.parse("04:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
    val endDayTime = LocalTime.parse("04:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

    val startWeekTime = LocalTime.parse("02:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
    val endWeekTime = LocalTime.parse("02:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

    val i = 0
    while (i<1){
        val dateTime = LocalDateTime.now()
        println(dateTime)

        if((dateTime.toLocalTime().isAfter(startDayTime)) and (dateTime.toLocalTime().isBefore(endDayTime))){
            dailyCalcs()
        } else if ((dateTime.dayOfWeek == DayOfWeek.MONDAY) and (dateTime.toLocalTime().isAfter(startWeekTime)) and (dateTime.toLocalTime().isBefore(endWeekTime))){
            weeklyCalcs()
        }

        Thread.sleep(5_000)
    }

}