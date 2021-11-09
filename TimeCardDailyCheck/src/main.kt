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

import org.apache.commons.mail.EmailAttachment
import org.apache.commons.mail.EmailException
import org.apache.commons.mail.HtmlEmail
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.UnknownHostException
import java.security.PrivilegedActionException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import javax.mail.MessagingException

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

//this code will run daily at 4:00 AM to ensure that no-one missed an in or out the previous day

fun readExcel(): MutableList<MutableList<Any>>{

    val today = LocalDate.now()
    var previousMonday: LocalDate = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
    var fileName = "${previousMonday}.xlsx"

    val myxlsx = FileInputStream("/C:/Users/ophillips/Documents/$fileName")

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

    val dataLocalDateTime: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())
    for (tap in excelData.indices){
        var dateTime = LocalDateTime.parse(excelData[tap][6] + "T" + excelData[tap][5], DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        dataLocalDateTime.add(mutableListOf(excelData[tap][0], excelData[tap][1], excelData[tap][2], excelData[tap][3], excelData[tap][4], dateTime))
    }

    dataLocalDateTime.removeAt(0)

    var timeStart = LocalDateTime.now().minus(20, ChronoUnit.DAYS)

    val dataSorted: MutableList<MutableList<Any>> = mutableListOf(mutableListOf())

    for (i in dataLocalDateTime.indices) {
        val dateTime = LocalDateTime.parse(dataLocalDateTime[i][5].toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        if (dateTime.isAfter(timeStart)){
            dataSorted.add(dataLocalDateTime[i])
        }
    }

    dataSorted.removeAt(0)

    return dataSorted
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

fun checkInOut(empNum: String, data: MutableList<MutableList<Any>>): String{



    var checkIn: Boolean = false
    var checkOut: Boolean = false

    for (tap in data.indices){
        if (data[tap][2] == empNum){
            if (data[tap][3] == "In"){
                checkIn = true
            } else if (data[tap][3] == "Out"){
                checkOut = true
            }
        }
    }



    if ((checkIn) and (checkOut)){
        return "In and Out"
    } else if (!checkIn){
        return "Missed In"
    } else if (!checkOut){
        return "Missed Out"
    }else{
        return "OOPS"
    }
}

fun makeDailyExcel(missedList: MutableList<MutableList<String>>) {
    //declare column headers for both the first and second sheet
    val columns1 =
        arrayOf("Name", "Employee Number", "Issue")

    val xlWb = XSSFWorkbook() //create a workbook with name xlWb
    val sheet1 = xlWb.createSheet("Missed Taps") //create sheet1 withing the workbook with title "Week Hours"

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

    //create rows to be filled by headers
    val headerRow1 = sheet1.createRow(0)

    //go through header list and set them as the sheet headers
    for (col in columns1.indices) {
        val cell = headerRow1.createCell(col)
        cell.setCellValue(columns1[col])
        cell.cellStyle = headerCellStyle //set font style to one declared above
        sheet1.autoSizeColumn(col)
    }

    //populate sheet with list information from weeklyHours list
    var rowIdx1 = 1
    for (missed in missedList.indices) {
        val row = sheet1.createRow(rowIdx1++) //create row
        for (col in missedList[missed].indices) {
            row.createCell(col).setCellValue(missedList[missed][col]) //fill columns
        }
    }


    //auto size columns to make things look nice
    for (col in columns1.indices) {
        sheet1.autoSizeColumn(col)
    }

    val today = LocalDate.now()
    val fileName = "testDailyCheck.xlsx"
    //save the file to this location
    val output = FileOutputStream("/C:/Users/ophillips/Documents/$fileName") //TODO update this file path
    xlWb.write(output)

    //close the workbook
    xlWb.close()

}

fun emailExcel() {

    val today = LocalDate.now()
    val fileName = "$today.xlsx"

    //"ASibbald@toromont.com", "BBrkovich@toromont.com", "BHall@toromont.com", "DPitre@toromont.com", "GHanna@toromont.com", "LKhalil@toromont.com", "MFratelli@toromont.com", "MGuimont@toromont.com", "SLinhardt@toromont.com")

    //MUST NOT BE CONNECTED TO THE INTRANET
    val email = HtmlEmail() //create email type
    email.hostName = "smtp.gmail.com" //connect to gmail server
    email.setSmtpPort(587) //sets server type
    email.setAuthentication("remantimesheets@gmail.com", "REMAN123") //username and password for email used to send
    email.isSSLOnConnect = true //more server stuff (just found it on google)
    email.setFrom("remantimesheets@gmail.com") //set "from" for the email
    email.subject = "Daily Missed Time Card Taps" //email subject
    email.setMsg("Please see the attached excel document of missed tap instances.\n\nCheck with the techs in the list and input their missed instance into the timeclock to ensure they get paid.") //email body text
    email.addTo("ASibbald@toromont.com")
    email.addTo("BBrkovich@toromont.com")
    email.addTo("MFratelli@toromont.com")
    email.addTo("MGuimont@toromont.com")
    email.addCc("ophillips@toromont.com") //email address that the email will be sent to //TODO update to supervisors
    email.addCc("TStella@toromont.com")
    val attachment = EmailAttachment() //create an attachment for the email
    attachment.path =
        "/C:/Users/ophillips/Documents/$fileName" //attach the excel document created in the previous function //TODO update file path
    email.attach(attachment) //attach the attachment to the email

    try{
        email.send()
    }catch(e: PrivilegedActionException){
//        val window = commentOK("Email Not Sent, Check Internet Connection")
//        window.isAlwaysOnTop = true
//        window.isVisible = true
    }catch(e: EmailException){
//        val window = commentOK("Email Not Sent, Check Internet Connection")
//        window.isAlwaysOnTop = true
//        window.isVisible = true
    }catch(e: MessagingException){
//        val window = commentOK("Email Not Sent, Check Internet Connection")
//        window.isAlwaysOnTop = true
//        window.isVisible = true
    }catch(e: UnknownHostException){
//        val window = commentOK("Email Not Sent, Check Internet Connection")
//        window.isAlwaysOnTop = true
//        window.isVisible = true
    }

}

fun main(){
    //load in file
    val data = readExcel()

    //get date of interest
    val dateOfInterest = LocalDate.now().minus(1, ChronoUnit.DAYS)

    val empList = employeeList(data)

    var missedList: MutableList<MutableList<String>> = mutableListOf(mutableListOf())

    for (emp in empList.indices){
        val missed = checkInOut(empList[emp][1], data)
        if ((missed == "Missed In") or (missed == "Missed Out")){
            missedList.add(mutableListOf(empList[emp][0], empList[emp][1], missed))
        }
    }

    if (missedList.size > 1){
        missedList.removeAt(0)
        makeDailyExcel(missedList)
        emailExcel()
    }

}