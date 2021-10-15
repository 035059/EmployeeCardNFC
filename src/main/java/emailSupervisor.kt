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
import org.apache.commons.mail.HtmlEmail
import java.time.LocalDate
import java.time.LocalTime

class emailSupervisor(name: String, empNum: String, status: String, supervisorEmail: String) {

    private fun sendEmail(name: String, empNum: String, status: String, supervisorEmail: String){

        val time = LocalTime.now().toString().substring(0,8)
        val date = LocalDate.now()

        val email = HtmlEmail() //create email type
        email.hostName = "smtp.gmail.com" //connect to gmail server
        email.setSmtpPort(587) //sets server type
        email.isStartTLSEnabled = true
        email.isStartTLSRequired = true
        email.setAuthentication("remantimesheets@gmail.com", "REMAN123") //username and password for email used to send
        //email.isSSLOnConnect = true //more server stuff (just found it on google)
        email.setFrom("remantimesheets@gmail.com") //set "from" for the email
        email.subject = "No ID Sign-${status} for $name" //email subject
        email.setMsg("Please log into the timeclock and input the following information:\n\nEmployee Name: $name\n\nEmployee Number: $empNum\n\nTime: $time\n\nDate: $date\n\nSign-In or Sign-Out: $status\n\nThank You\n\nthis email should go to $supervisorEmail") //email body text
        email.addTo("ophillips@toromont.com") //email address that the email will be sent to
        email.send() //send the email

        val window = commentOK("Email Sent")
        window.isAlwaysOnTop = true
        window.isVisible = true
    }

    val bink = sendEmail(name, empNum, status, supervisorEmail)

}