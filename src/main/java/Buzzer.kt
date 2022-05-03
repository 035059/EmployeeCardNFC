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

import com.pi4j.io.gpio.*
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val start225: LocalTime = LocalTime.parse("02:25:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end225: LocalTime = LocalTime.parse("02:25:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start230: LocalTime = LocalTime.parse("02:30:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end230: LocalTime = LocalTime.parse("02:30:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start500: LocalTime = LocalTime.parse("05:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end500: LocalTime = LocalTime.parse("05:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start530: LocalTime = LocalTime.parse("05:30:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end530: LocalTime = LocalTime.parse("05:30:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start600: LocalTime = LocalTime.parse("06:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end600: LocalTime = LocalTime.parse("06:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start730: LocalTime = LocalTime.parse("07:30:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end730: LocalTime = LocalTime.parse("07:30:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start740: LocalTime = LocalTime.parse("07:40:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end740: LocalTime = LocalTime.parse("07:40:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start800: LocalTime = LocalTime.parse("08:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end800: LocalTime = LocalTime.parse("08:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start810: LocalTime = LocalTime.parse("08:10:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end810: LocalTime = LocalTime.parse("08:10:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start900: LocalTime = LocalTime.parse("09:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end900: LocalTime = LocalTime.parse("09:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start910: LocalTime = LocalTime.parse("09:10:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end910: LocalTime = LocalTime.parse("09:10:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1000: LocalTime = LocalTime.parse("10:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1000: LocalTime = LocalTime.parse("10:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1030: LocalTime = LocalTime.parse("10:30:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1030: LocalTime = LocalTime.parse("10:30:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1045: LocalTime = LocalTime.parse("10:45:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1045: LocalTime = LocalTime.parse("10:45:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1115: LocalTime = LocalTime.parse("11:15:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1115: LocalTime = LocalTime.parse("11:15:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1125: LocalTime = LocalTime.parse("11:25:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1125: LocalTime = LocalTime.parse("11:25:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1200: LocalTime = LocalTime.parse("12:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1200: LocalTime = LocalTime.parse("12:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1300: LocalTime = LocalTime.parse("13:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1300: LocalTime = LocalTime.parse("13:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1310: LocalTime = LocalTime.parse("13:10:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1310: LocalTime = LocalTime.parse("13:10:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1330: LocalTime = LocalTime.parse("13:30:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1330: LocalTime = LocalTime.parse("13:30:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1340: LocalTime = LocalTime.parse("13:40:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1340: LocalTime = LocalTime.parse("13:40:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1400: LocalTime = LocalTime.parse("14:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1400: LocalTime = LocalTime.parse("14:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1410: LocalTime = LocalTime.parse("14:10:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1410: LocalTime = LocalTime.parse("14:10:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1525: LocalTime = LocalTime.parse("15:25:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1525: LocalTime = LocalTime.parse("15:25:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1530: LocalTime = LocalTime.parse("15:30:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1530: LocalTime = LocalTime.parse("15:30:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1555: LocalTime = LocalTime.parse("15:55:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1555: LocalTime = LocalTime.parse("15:55:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1600: LocalTime = LocalTime.parse("16:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1600: LocalTime = LocalTime.parse("16:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1610: LocalTime = LocalTime.parse("16:10:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1610: LocalTime = LocalTime.parse("16:10:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1825: LocalTime = LocalTime.parse("18:25:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1825: LocalTime = LocalTime.parse("18:25:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1830: LocalTime = LocalTime.parse("18:30:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1830: LocalTime = LocalTime.parse("18:30:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start1900: LocalTime = LocalTime.parse("19:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end1900: LocalTime = LocalTime.parse("19:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start2100: LocalTime = LocalTime.parse("21:00:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end2100: LocalTime = LocalTime.parse("21:00:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start2110: LocalTime = LocalTime.parse("21:10:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end2110: LocalTime = LocalTime.parse("21:10:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start2330: LocalTime = LocalTime.parse("23:30:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end2330: LocalTime = LocalTime.parse("23:30:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

val start2340: LocalTime = LocalTime.parse("23:40:00", DateTimeFormatter.ISO_LOCAL_TIME)
val end2340: LocalTime = LocalTime.parse("23:40:05.02", DateTimeFormatter.ISO_LOCAL_TIME)

fun runBuzzer(pin: GpioPinDigitalOutput){
    pin.pulse(5000)
}

fun checkBuzzer(checkedDateTime: LocalDateTime, pin: GpioPinDigitalOutput){
    if ((checkedDateTime.dayOfWeek == DayOfWeek.MONDAY) or (checkedDateTime.dayOfWeek == DayOfWeek.TUESDAY) or (checkedDateTime.dayOfWeek == DayOfWeek.WEDNESDAY) or (checkedDateTime.dayOfWeek == DayOfWeek.THURSDAY)){
        if((checkedDateTime.toLocalTime().isAfter(start225)) and (checkedDateTime.toLocalTime().isBefore(end225))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start230)) and (checkedDateTime.toLocalTime().isBefore(end230))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start500)) and (checkedDateTime.toLocalTime().isBefore(end500))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start530)) and (checkedDateTime.toLocalTime().isBefore(end530))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start730)) and (checkedDateTime.toLocalTime().isBefore(end730))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start740)) and (checkedDateTime.toLocalTime().isBefore(end740))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start800)) and (checkedDateTime.toLocalTime().isBefore(end800))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start810)) and (checkedDateTime.toLocalTime().isBefore(end810))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1000)) and (checkedDateTime.toLocalTime().isBefore(end1000))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1030)) and (checkedDateTime.toLocalTime().isBefore(end1030))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1045)) and (checkedDateTime.toLocalTime().isBefore(end1045))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1115)) and (checkedDateTime.toLocalTime().isBefore(end1115))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1300)) and (checkedDateTime.toLocalTime().isBefore(end1300))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1310)) and (checkedDateTime.toLocalTime().isBefore(end1310))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1330)) and (checkedDateTime.toLocalTime().isBefore(end1330))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1340)) and (checkedDateTime.toLocalTime().isBefore(end1340))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1525)) and (checkedDateTime.toLocalTime().isBefore(end1525))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1530)) and (checkedDateTime.toLocalTime().isBefore(end1530))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1555)) and (checkedDateTime.toLocalTime().isBefore(end1555))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1600)) and (checkedDateTime.toLocalTime().isBefore(end1600))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1825)) and (checkedDateTime.toLocalTime().isBefore(end1825))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1900)) and (checkedDateTime.toLocalTime().isBefore(end1900))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start2100)) and (checkedDateTime.toLocalTime().isBefore(end2100))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start2110)) and (checkedDateTime.toLocalTime().isBefore(end2110))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start2330)) and (checkedDateTime.toLocalTime().isBefore(end2330))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start2340)) and (checkedDateTime.toLocalTime().isBefore(end2340))){
            runBuzzer(pin)
        }
    }else if ((checkedDateTime.dayOfWeek == DayOfWeek.FRIDAY) or (checkedDateTime.dayOfWeek == DayOfWeek.SATURDAY) or (checkedDateTime.dayOfWeek == DayOfWeek.SUNDAY)){
        if((checkedDateTime.toLocalTime().isAfter(start225)) and (checkedDateTime.toLocalTime().isBefore(end225))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start230)) and (checkedDateTime.toLocalTime().isBefore(end230))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start600)) and (checkedDateTime.toLocalTime().isBefore(end600))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start900)) and (checkedDateTime.toLocalTime().isBefore(end900))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start910)) and (checkedDateTime.toLocalTime().isBefore(end910))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1125)) and (checkedDateTime.toLocalTime().isBefore(end1125))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1200)) and (checkedDateTime.toLocalTime().isBefore(end1200))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1400)) and (checkedDateTime.toLocalTime().isBefore(end1400))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1410)) and (checkedDateTime.toLocalTime().isBefore(end1410))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1600)) and (checkedDateTime.toLocalTime().isBefore(end1600))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1610)) and (checkedDateTime.toLocalTime().isBefore(end1610))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1825)) and (checkedDateTime.toLocalTime().isBefore(end1825))){
            runBuzzer(pin)
        }else if((checkedDateTime.toLocalTime().isAfter(start1830)) and (checkedDateTime.toLocalTime().isBefore(end1830))){
            runBuzzer(pin)
        }
    }
}


