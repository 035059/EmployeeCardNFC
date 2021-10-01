import org.apache.commons.mail.EmailAttachment
import org.apache.commons.mail.HtmlEmail
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import kotlin.math.floor


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

/*
To Do List
TODO read in data from global excel (or each local excel)
TODO put hours into an excel sheet
TODO email excel sheet to G
TODO create new global excel to be populated the next week
*/

//Set global lists to be filled by other functions. weeklyHours is the weekly summary, shiftHours is the breakdown of each shift
val weeklyHours: MutableList<List<String>> = mutableListOf()

val shiftHours: MutableList<List<String>> = mutableListOf()

fun readExcel(): MutableList<MutableList<String>>{

    val today = LocalDate.now()
    var previousMonday: LocalDate = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
    var fileName = "${previousMonday}.xlsx"

    val myxlsx = FileInputStream("C:/Users/ophillips/Documents/$fileName")

    val workbook = XSSFWorkbook(myxlsx)
    val sheet = workbook.getSheetAt(0)

    val numRows = sheet.lastRowNum
    val numColumns = sheet.getRow(0).lastCellNum
    var excelData: MutableList<MutableList<String>> = MutableList(numRows){mutableListOf<String>("","","","","","","")}

    for (i in 1..numRows){
        var row: Row = sheet.getRow(i)
        //println("Row: $row")
        for (j in 0 until numColumns){
          var cell: Cell = row.getCell(j)
          excelData[i-1][j] = cell.stringCellValue
        }
    }

    return excelData
}

fun sortData(data: MutableList<MutableList<String>>): MutableList<MutableList<Any>>{

    val dataLocalDateTime: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())
    val localDateTimes: MutableList<LocalDateTime> = mutableListOf()
    val sortedData: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())

    for (tap in data.indices){
        var dateTime = LocalDateTime.parse(data[tap][6] + "T" + data[tap][5],DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        dataLocalDateTime.add(mutableListOf(data[tap][0], data[tap][1], data[tap][2], data[tap][3], data[tap][4], dateTime))
        localDateTimes.add(dateTime)
    }

    dataLocalDateTime.removeAt(0)

    val dateTimeStrToLocalDateTime: (String) -> LocalDateTime = {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    localDateTimes.sort()

    for (i in localDateTimes.indices){
        for (j in dataLocalDateTime.indices){
            if (localDateTimes[i] == dataLocalDateTime[j][5]){
                sortedData.add(dataLocalDateTime[j])
            }
        }
    }

    sortedData.removeAt(0)


    return sortedData
}

fun employeeList(data: MutableList<MutableList<Any>>): MutableList<MutableList<String>>{

    var empList: MutableList<MutableList<String>> = mutableListOf(mutableListOf())

    for (i in data.indices) {
        if (mutableListOf(data[i][1], data[i][2], data[i][4]) !in empList){
            empList.add(mutableListOf(data[i][1].toString(), data[i][2].toString(), data[i][4].toString()))
        }
    }
    empList.removeAt(0)
    return empList

}

// This function allows for the rounding of any double to the nearest .25
fun quarterRound(i: Double): Double {
    return when (i % 1) {
        in 0.0..0.125 -> floor(i)
        in 0.0..0.375 -> floor(i) + .25
        in 0.0..0.625 -> floor(i) + .5
        in 0.0..0.875 -> floor(i) + .75
        else -> floor(i) + 1
    }
}

fun inOrOut(name: String, empNum: String, data: MutableList<MutableList<Any>>): Pair<MutableList<MutableList<Any>>, MutableList<MutableList<Any>>>{
    val inTime: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())
    val outTime: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())

    for (tap in data.indices){
        if (data[tap][2] == empNum){
            //
            if (data[tap][3] == "In"){
                inTime.add(data[tap])
            }
            else{
                outTime.add(data[tap])
            }
        }
    }

    inTime.removeAt(0)
    outTime.removeAt(0)

    return Pair(inTime,outTime)

}

fun removeDuplicates (inTime: MutableList<MutableList<Any>>, outTime: MutableList<MutableList<Any>>): Pair<MutableList<MutableList<Any>>, MutableList<MutableList<Any>>>{
    //look through both inTime and outTime and check dates
    //for inTime, only keep the earliest        for outTime keep the latest
    val inTimeF: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())
    val outTimeF: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())
    val inDates: MutableList<String> = mutableListOf()
    val outDates: MutableList<String> = mutableListOf()


    for (tap in inTime.indices){
        val date = LocalDateTime.parse(inTime[tap][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate().toString()
        if (date !in inDates){
            inDates.add(date)
            inTimeF.add(inTime[tap])
        }
    }

    for (tap in outTime.lastIndex downTo 0){
        val date = LocalDateTime.parse(outTime[tap][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate().toString()
        if (date !in outDates){
            outDates.add(date)
            outTimeF.add(outTime[tap])
        }
    }

    inTimeF.removeAt(0)
    outTimeF.removeAt(0)
    outTimeF.reverse()

return Pair(inTimeF, outTimeF)
}

fun removeDuplicatesNight (inTime: MutableList<MutableList<Any>>, outTime: MutableList<MutableList<Any>>): Pair<MutableList<MutableList<Any>>, MutableList<MutableList<Any>>>{
    //look through both inTime and outTime and check dates
    //for inTime, only keep the earliest        for outTime keep the latest
    val inTimeF: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())
    val outTimeF: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())
    val inDates: MutableList<String> = mutableListOf()
    val outDates: MutableList<String> = mutableListOf()


    for (tap in inTime.indices){
        val date = LocalDateTime.parse(inTime[tap][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate().toString()
        if (date !in inDates){
            inDates.add(date)
            inTimeF.add(inTime[tap])
        }
    }

//    for (tap in outTime.lastIndex downTo 0){
//        val date = LocalDateTime.parse(outTime[tap][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate().toString()
//        if (date !in outDates){
//            outDates.add(date)
//            outTimeF.add(outTime[tap])
//        }
//    }

// instead of just checking date, check previous noon (12pm - 12pm instead of 12am - 12am)
    for (tap in outTime.lastIndex downTo 0){
        val date = LocalDateTime.parse(outTime[tap][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
//        val midnight = date.toLocalDate().minus(1, ChronoUnit.DAYS)
//        if (date.isAfter(date.toLocalDate().minus(1, ChronoUnit.DAYS)))

        if (date.toLocalTime().isBefore(LocalTime.parse("12:00:00", DateTimeFormatter.ISO_LOCAL_TIME))){
            val shiftDate = date.toLocalDate().minus(1,ChronoUnit.DAYS).toString()
            if (shiftDate!in outDates){
                outDates.add(shiftDate)
                outTimeF.add(outTime[tap])
            }
        } else{
            val shiftDate = date.toLocalDate().toString()
            if (shiftDate !in outDates){
                outDates.add(shiftDate)
                outTimeF.add(outTime[tap])
            }
        }

    }


    inTimeF.removeAt(0)
    outTimeF.removeAt(0)
    outTimeF.reverse()

    return Pair(inTimeF, outTimeF)
}

fun dayShiftHours(name: String, empNum: String, data: MutableList<MutableList<Any>>){
    //separate ins and outs
    val (inTime, outTime) = inOrOut(name, empNum, data)

    //remove duplicates
    val (inTimeF, outTimeF) = removeDuplicates(inTime, outTime)

    //initialize lists to be populated throughout function
    val normHours: MutableList<Float> = mutableListOf() //normal hours
    val totHours: MutableList<Float> = mutableListOf() //total hours
    val otHours: MutableList<Float> = mutableListOf() //overtime hours

    var latesCount: Int = 0

    //go through the in and out time arrays
    for (day in inTimeF.indices) {
        //create date, time, and datetime for each event
        val inDateTime = LocalDateTime.parse(inTimeF[day][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val outDateTime = LocalDateTime.parse(outTimeF[day][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        //create a LocalDateTime value for overtime start based on the date and shift
        val shiftStart1 = inDateTime.toLocalDate().toString() + " " + "05:45:00"
        val shiftStart = LocalDateTime.parse(shiftStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        //create a LocalDateTime value for overtime start based on the date and shift
        val otStart1 = inDateTime.toLocalDate().toString() + " " + "16:15:00"
        val otStart = LocalDateTime.parse(otStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        //total hours is the length from in time to out time for each day
        val totHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, outDateTime) - 30).toFloat()) / 60
        totHours.add(totHoursDay)

        //create a list of important values about this shift
        val shift: MutableList<String> = mutableListOf(
            empNum,
            name,
            "Day",
            inDateTime.toLocalDate().toString(),
            inDateTime.toLocalTime().toString(),
            outDateTime.toLocalDate().toString(),
            outDateTime.toLocalTime().toString()
        )

        //calculate normal and overtime hours
        if (outDateTime.isAfter(otStart)) { //if signed out after overtime start, normal is between in and otstart, ot is between otstart and out
            val normHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, otStart) - 30).toFloat()) / 60
            normHours.add(normHoursDay)
            shift.add(quarterRound(normHoursDay.toDouble()).toString()) //add normal hours to shift info list

            val otHoursDay: Float = (ChronoUnit.MINUTES.between(otStart, outDateTime).toFloat()) / 60
            otHours.add(otHoursDay)
            shift.add(quarterRound(otHoursDay.toDouble()).toString()) //add overtime hours to shift info list
        } else { //shift ends before overtime starts, all hours are normal hours
            val normHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, outDateTime) - 30).toFloat()) / 60
            normHours.add(normHoursDay)
            shift.add(quarterRound(normHoursDay.toDouble()).toString()) //add normal hours to shift info list

            val otHoursDay = 0.0F
            otHours.add(otHoursDay)
            shift.add(quarterRound(otHoursDay.toDouble()).toString()) //add overtime hours to shift info list
        }
        shift.add(quarterRound(totHoursDay.toDouble()).toString())

        if (inDateTime.isAfter(shiftStart)){
            shift.add("Late")
            latesCount ++
        } else {
            shift.add("On Time")
        }

        shiftHours.add(shift) //add the shift list to the overall shiftHours directory
    }
    //create a summary of this employee's total weekly hours
    val week: List<String> = listOf(
        empNum,
        name,
        "Day",
        quarterRound(normHours.sum().toDouble()).toString(),
        quarterRound(otHours.sum().toDouble()).toString(),
        quarterRound(totHours.sum().toDouble()).toString(),
        latesCount.toString()
    )
    weeklyHours.add(week) //add the week list to the overall weeklyHours directory
}

fun nightShiftHours(name: String, empNum: String, data: MutableList<MutableList<Any>>){
    //separate ins and outs
    val (inTime, outTime) = inOrOut(name, empNum, data)

    //remove duplicates
    val (inTimeF, outTimeF) = removeDuplicatesNight(inTime, outTime)

    //initialize lists to be populated throughout function
    val normHours: MutableList<Float> = mutableListOf() //normal hours
    val totHours: MutableList<Float> = mutableListOf() //total hours
    val otHours: MutableList<Float> = mutableListOf() //overtime hours

    var latesCount: Int = 0

    //go through the in and out time arrays
    for (day in inTimeF.indices) {
        //create date, time, and datetime for each event
        val inDateTime = LocalDateTime.parse(inTimeF[day][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val outDateTime = LocalDateTime.parse(outTimeF[day][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        //create a LocalDateTime value for overtime start based on the date and shift
        val shiftStart1 = inDateTime.toLocalDate().toString() + " " + "15:30:00"
        val shiftStart = LocalDateTime.parse(shiftStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        //create a LocalDateTime value for overtime start based on the date and shift
        val otStart1 = inDateTime.toLocalDate().plus(1,ChronoUnit.DAYS).toString() + " " + "02:00:00"
        val otStart = LocalDateTime.parse(otStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        //total hours is the length from in time to out time for each day
        val totHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, outDateTime) - 30).toFloat()) / 60
        totHours.add(totHoursDay)

        //create a list of important values about this shift
        val shift: MutableList<String> = mutableListOf(
            empNum,
            name,
            "Night",
            inDateTime.toLocalDate().toString(),
            inDateTime.toLocalTime().toString(),
            outDateTime.toLocalDate().toString(),
            outDateTime.toLocalTime().toString()
        )

        //calculate normal and overtime hours
        if (outDateTime.isAfter(otStart)) { //if signed out after overtime start, normal is between in and otstart, ot is between otstart and out
            val normHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, otStart) - 30).toFloat()) / 60
            normHours.add(normHoursDay)
            shift.add(quarterRound(normHoursDay.toDouble()).toString()) //add normal hours to shift info list

            val otHoursDay: Float = (ChronoUnit.MINUTES.between(otStart, outDateTime).toFloat()) / 60
            otHours.add(otHoursDay)
            shift.add(quarterRound(otHoursDay.toDouble()).toString()) //add overtime hours to shift info list
        } else { //shift ends before overtime starts, all hours are normal hours
            val normHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, outDateTime) - 30).toFloat()) / 60
            normHours.add(normHoursDay)
            shift.add(quarterRound(normHoursDay.toDouble()).toString()) //add normal hours to shift info list

            val otHoursDay = 0.0F
            otHours.add(otHoursDay)
            shift.add(quarterRound(otHoursDay.toDouble()).toString()) //add overtime hours to shift info list
        }
        shift.add(quarterRound(totHoursDay.toDouble()).toString())

        if (inDateTime.isAfter(shiftStart)){
            shift.add("Late")
            latesCount ++
        } else {
            shift.add("On Time")
        }

        shiftHours.add(shift) //add the shift list to the overall shiftHours directory
    }
    //create a summary of this employee's total weekly hours
    val week: List<String> = listOf(
        empNum,
        name,
        "Night",
        quarterRound(normHours.sum().toDouble()).toString(),
        quarterRound(otHours.sum().toDouble()).toString(),
        quarterRound(totHours.sum().toDouble()).toString(),
        latesCount.toString()
    )
    weeklyHours.add(week) //add the week list to the overall weeklyHours directory

}

fun weekendShiftHours(name: String, empNum: String, data: MutableList<MutableList<Any>>){
    //separate ins and outs
    val (inTime, outTime) = inOrOut(name, empNum, data)

    //remove duplicates
    val (inTimeF, outTimeF) = removeDuplicatesNight(inTime, outTime)

    //initialize lists to be populated throughout function
    val normHours: MutableList<Float> = mutableListOf() //normal hours
    val totHours: MutableList<Float> = mutableListOf() //total hours
    val otHours: MutableList<Float> = mutableListOf() //overtime hours

    var latesCount: Int = 0

    //go through the in and out time arrays
    for (day in inTimeF.indices) {
        //create date, time, and datetime for each event
        val inDateTime = LocalDateTime.parse(inTimeF[day][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val outDateTime = LocalDateTime.parse(outTimeF[day][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)


        val shiftStart: LocalDateTime = if (inDateTime.dayOfWeek == DayOfWeek.FRIDAY) {
            val shiftStart1 = inDateTime.toLocalDate().toString() + " " + "07:00:00" //fridays are 7:00-19:30
            val shiftStartFri = LocalDateTime.parse(shiftStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            shiftStartFri
        } else {
            val shiftStart1 = inDateTime.toLocalDate().toString() + " " + "06:00:00" //sat and sun are 6:00-6:30
            val shiftStartEnd = LocalDateTime.parse(shiftStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            shiftStartEnd
        }


        val otStart: LocalDateTime = if (inDateTime.dayOfWeek == DayOfWeek.FRIDAY) {
            val otStart1 = inDateTime.toLocalDate().toString() + " " + "19:30:00" //fridays are 7:00-19:30
            val otStartFri = LocalDateTime.parse(otStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            otStartFri
        } else {
            val otStart1 = inDateTime.toLocalDate().toString() + " " + "18:30:00" //sat and sun are 6:00-6:30
            val otStartEnd = LocalDateTime.parse(otStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            otStartEnd
        }

        //total hours is the length from in time to out time for each day
        val totHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, outDateTime) - 30).toFloat()) / 60
        totHours.add(totHoursDay)

        //create a list of important values about this shift
        val shift: MutableList<String> = mutableListOf(
            empNum,
            name,
            "Weekend",
            inDateTime.toLocalDate().toString(),
            inDateTime.toLocalTime().toString(),
            outDateTime.toLocalDate().toString(),
            outDateTime.toLocalTime().toString()
        )

        //calculate normal and overtime hours
        if (outDateTime.isAfter(otStart)) { //if signed out after overtime start, normal is between in and otstart, ot is between otstart and out
            val normHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, otStart) - 30).toFloat()) / 60
            normHours.add(normHoursDay)
            shift.add(quarterRound(normHoursDay.toDouble()).toString()) //add normal hours to shift info list

            val otHoursDay: Float = (ChronoUnit.MINUTES.between(otStart, outDateTime).toFloat()) / 60
            otHours.add(otHoursDay)
            shift.add(quarterRound(otHoursDay.toDouble()).toString()) //add overtime hours to shift info list
        } else { //shift ends before overtime starts, all hours are normal hours
            val normHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, outDateTime) - 30).toFloat()) / 60
            normHours.add(normHoursDay)
            shift.add(quarterRound(normHoursDay.toDouble()).toString()) //add normal hours to shift info list

            val otHoursDay = 0.0F
            otHours.add(otHoursDay)
            shift.add(quarterRound(otHoursDay.toDouble()).toString()) //add overtime hours to shift info list
        }
        shift.add(quarterRound(totHoursDay.toDouble()).toString())

        if (inDateTime.isAfter(shiftStart)){
            shift.add("Late")
            latesCount ++
        } else {
            shift.add("On Time")
        }

        shiftHours.add(shift) //add the shift list to the overall shiftHours directory
    }
    //create a summary of this employee's total weekly hours
    val week: List<String> = listOf(
        empNum,
        name,
        "Weekend",
        quarterRound(normHours.sum().toDouble()).toString(),
        quarterRound(otHours.sum().toDouble()).toString(),
        quarterRound(totHours.sum().toDouble()).toString(),
        latesCount.toString()
    )
    weeklyHours.add(week) //add the week list to the overall weeklyHours directory
}

fun fivedayShiftHours(name: String, empNum: String, data: MutableList<MutableList<Any>>){
    //separate ins and outs
    val (inTime, outTime) = inOrOut(name, empNum, data)

    //remove duplicates
    val (inTimeF, outTimeF) = removeDuplicates(inTime, outTime)

    //initialize lists to be populated throughout function
    val normHours: MutableList<Float> = mutableListOf() //normal hours
    val totHours: MutableList<Float> = mutableListOf() //total hours
    val otHours: MutableList<Float> = mutableListOf() //overtime hours

    var latesCount: Int = 0

    //go through the in and out time arrays
    for (day in inTimeF.indices) {
        //create date, time, and datetime for each event
        val inDateTime = LocalDateTime.parse(inTimeF[day][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val outDateTime = LocalDateTime.parse(outTimeF[day][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        //create a LocalDateTime value for overtime start based on the date and shift
        val shiftStart1 = inDateTime.toLocalDate().toString() + " " + "05:45:00"
        val shiftStart = LocalDateTime.parse(shiftStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        //create a LocalDateTime value for overtime start based on the date and shift
        val otStart1 = inDateTime.toLocalDate().toString() + " " + "14:15:00"
        val otStart = LocalDateTime.parse(otStart1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        //total hours is the length from in time to out time for each day
        val totHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, outDateTime) - 30).toFloat()) / 60
        totHours.add(totHoursDay)

        //create a list of important values about this shift
        val shift: MutableList<String> = mutableListOf(
            empNum,
            name,
            "Five Day",
            inDateTime.toLocalDate().toString(),
            inDateTime.toLocalTime().toString(),
            outDateTime.toLocalDate().toString(),
            outDateTime.toLocalTime().toString()
        )

        //calculate normal and overtime hours
        if (outDateTime.isAfter(otStart)) { //if signed out after overtime start, normal is between in and otstart, ot is between otstart and out
            val normHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, otStart) - 30).toFloat()) / 60
            normHours.add(normHoursDay)
            shift.add(quarterRound(normHoursDay.toDouble()).toString()) //add normal hours to shift info list

            val otHoursDay: Float = (ChronoUnit.MINUTES.between(otStart, outDateTime).toFloat()) / 60
            otHours.add(otHoursDay)
            shift.add(quarterRound(otHoursDay.toDouble()).toString()) //add overtime hours to shift info list
        } else { //shift ends before overtime starts, all hours are normal hours
            val normHoursDay: Float = ((ChronoUnit.MINUTES.between(inDateTime, outDateTime) - 30).toFloat()) / 60
            normHours.add(normHoursDay)
            shift.add(quarterRound(normHoursDay.toDouble()).toString()) //add normal hours to shift info list

            val otHoursDay = 0.0F
            otHours.add(otHoursDay)
            shift.add(quarterRound(otHoursDay.toDouble()).toString()) //add overtime hours to shift info list
        }
        shift.add(quarterRound(totHoursDay.toDouble()).toString())

        if (inDateTime.isAfter(shiftStart)){
            shift.add("Late")
            latesCount ++
        } else {
            shift.add("On Time")
        }

        shiftHours.add(shift) //add the shift list to the overall shiftHours directory
    }
    //create a summary of this employee's total weekly hours
    val week: List<String> = listOf(
        empNum,
        name,
        "Five Day",
        quarterRound(normHours.sum().toDouble()).toString(),
        quarterRound(otHours.sum().toDouble()).toString(),
        quarterRound(totHours.sum().toDouble()).toString(),
        latesCount.toString()
    )
    weeklyHours.add(week) //add the week list to the overall weeklyHours directory
}

fun makeExcel() {
    //declare column headers for both the first and second sheet
    val columns1 =
        arrayOf("Employee Number", "Name", "Shift", "Normal Hours", "Overtime Hours", "Total Hours", "Number of Lates")
    val columns2 = arrayOf(
        "Employee Number",
        "Name",
        "Shift",
        "In Date",
        "In Time",
        "Out Date",
        "Out Time",
        "Normal Hours",
        "Overtime Hours",
        "Total Hours",
        "Status"
    )

    val xlWb = XSSFWorkbook() //create a workbook with name xlWb
    val sheet1 = xlWb.createSheet("Week Hours") //create sheet1 withing the workbook with title "Week Hours"
    val sheet2 = xlWb.createSheet("Detailed Hours") //create sheet2 with title "Detailed Hours"

    //create a font for the headers
    val headerFont = xlWb.createFont()
    headerFont.bold = true //make it bold

    val headerCellStyle = xlWb.createCellStyle() //set the style for this font
    headerCellStyle.setFont(headerFont)
    headerCellStyle.fillForegroundColor = IndexedColors.GREY_25_PERCENT.getIndex() //grey highlight
    headerCellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
    //set borders around cells
    headerCellStyle.borderBottom = BorderStyle.MEDIUM
    headerCellStyle.borderTop = BorderStyle.MEDIUM
    headerCellStyle.borderLeft = BorderStyle.MEDIUM
    headerCellStyle.borderRight = BorderStyle.MEDIUM

    val fmt: DataFormat = xlWb.createDataFormat()
    val cellStyle: CellStyle = xlWb.createCellStyle()
    cellStyle.dataFormat = fmt.getFormat("@")

    val lateCellStyle = xlWb.createCellStyle()
    lateCellStyle.fillForegroundColor = IndexedColors.ROSE.getIndex()
    lateCellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
    lateCellStyle.dataFormat = fmt.getFormat("@")

    //create rows to be filled by headers
    val headerRow1 = sheet1.createRow(0)
    val headerRow2 = sheet2.createRow(0)

    //go through header list and set them as the sheet headers
    for (col in columns1.indices) {
        val cell = headerRow1.createCell(col)
        cell.setCellValue(columns1[col])
        cell.cellStyle = headerCellStyle //set font style to one declared above
        sheet1.autoSizeColumn(col)
    }

    //same for sheet 2
    for (col in columns2.indices) {
        val cell = headerRow2.createCell(col)
        cell.setCellValue(columns2[col])
        cell.cellStyle = headerCellStyle
        sheet2.autoSizeColumn(col)
    }

    //populate sheet with list information from weeklyHours list
    var rowIdx1 = 1
    for (employee in weeklyHours.indices) {
        val row = sheet1.createRow(rowIdx1++) //create row
        for (col in weeklyHours[employee].indices) {
            val cell = row.createCell(col)
            cell.setCellValue(weeklyHours[employee][col]) //fill columns
            cell.setCellStyle(cellStyle)
        }
    }

    //do the same for shiftHours list
    var rowIdx2 = 1
    for (job in shiftHours.indices) {
        val row = sheet2.createRow(rowIdx2++)
        for (col in shiftHours[job].indices) {
            val cell = row.createCell(col)
            cell.setCellValue(shiftHours[job][col]) //fill columns
            cell.setCellStyle(cellStyle)
            if (shiftHours[job][shiftHours[job].size-1] == "Late"){
                cell.cellStyle = lateCellStyle
            }
        }
    }

    //auto size columns to make things look nice
    for (col in columns1.indices) {
        sheet1.autoSizeColumn(col)
    }
    for (col in columns2.indices) {
        sheet2.autoSizeColumn(col)
    }

    //save the file to this location
    val output = FileOutputStream("C:/Users/ophillips/Desktop/WeeklyTaps.xlsx")
    xlWb.write(output)

    //close the workbook
    xlWb.close()

}

//sends the excel document as an email
fun sendEmail() {
    //MUST NOT BE CONNECTED TO THE INTRANET
    val email = HtmlEmail() //create email type
    email.hostName = "smtp.gmail.com" //connect to gmail server
    email.setSmtpPort(587) //sets server type
    email.setAuthentication("remantimesheets@gmail.com", "REMAN123") //username and password for email used to send
    email.isSSLOnConnect = true //more server stuff (just found it on google)
    email.setFrom("remantimesheets@gmail.com") //set "from" for the email
    email.subject = "Weekly In and Out Times" //email subject
    email.setMsg("Please see the attached excel document of weekly in and out times for each tech.") //email body text
    email.addTo("ophillips@toromont.com") //email address that the email will be sent to
    val attachment = EmailAttachment() //create an attachment for the email
    attachment.path =
        "C:/Users/ophillips/Desktop/WeeklyTaps.xlsx" //attach the excel document created in the previous function
    email.attach(attachment) //attach the attachment to the email
    email.send() //send the email
}

fun weeklyCalcs (args: Array<String>){

    val data = readExcel()

    val sortedData = sortData(data)

    val employeeList = employeeList(sortedData)

    for (emp in employeeList.indices){
        when (employeeList[emp][2]){
            "Day" -> dayShiftHours(employeeList[emp][0], employeeList[emp][1], sortedData)
            "Night" -> nightShiftHours(employeeList[emp][0], employeeList[emp][1], sortedData)
            "Weekend" -> weekendShiftHours(employeeList[emp][0], employeeList[emp][1], sortedData)
            "Five Day" -> fivedayShiftHours(employeeList[emp][0], employeeList[emp][1], sortedData)
            else -> println("Uh Oh")
        }
    }

    makeExcel()
    sendEmail()

}