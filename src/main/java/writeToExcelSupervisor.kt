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

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalTime


class writeToExcelSupervisor(ID: String, name: String, empNum: String, status: String, shift: String, time: String, date: String) {

    //excel file name convention: date of previous monday (date file is created) yyyy-mm-dd (eg. 2021-09-20.xlsx)

    //find excel name
    private fun getFileName(): String{
        val today = LocalDate.now()
        if (today.dayOfWeek == DayOfWeek.MONDAY){
            return today.toString()
        }
        else{
            var previousMonday: LocalDate = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
            return "${previousMonday}.xlsx"
        }
    }

    val excelFile: String = getFileName()

    //collect data into list
    val data = listOf(ID, name, empNum, status, shift, time, date)

    private fun fillExcel(data: List<String>, excelFile: String){
        //create file stream to excel
        val myxlsx = FileInputStream("/home/pi/Desktop/$excelFile")   //TODO update to real doc location

        //set workbook and sheet to populate
        val workbook = XSSFWorkbook(myxlsx)
        val sheet = workbook.getSheetAt(0)

        //must find the next row to fill with data
        val populateRow = sheet.lastRowNum + 1

        //create a row and fill it with data
        val row = sheet.createRow(populateRow)
        for (i in data.indices){
            row.createCell(i).setCellValue(data[i])
        }

        myxlsx.close()

        //set a spot to save the file
        val output_file = FileOutputStream("/home/pi/Desktop/$excelFile")

        //save the file to this locaiton
        workbook.write(output_file)

        //close the file
        output_file.close()

        val window = commentOK("${data[1]} Signed ${data[3]}")
        window.isAlwaysOnTop = true
        window.isVisible = true
    }

    val popExcel = fillExcel(data, excelFile)

}