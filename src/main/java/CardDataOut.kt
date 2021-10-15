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

import com.sun.jna.Library
import com.sun.jna.Native

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

//function to read card data from reader (taken from RF IDeas SDK example)
fun getActiveId32Out(lib: CardDataOut.`SampleClass$Reader`): String {
    val PRXDEVTYP_USB: Short = 0
    lib.SetDevTypeSrch(PRXDEVTYP_USB)
    if (lib.usbConnect() == 0) {
        println("\nReader not connected")
        return "0"
    }
    try {
        Thread.sleep(250)
    } catch (ex: InterruptedException) {
        Thread.currentThread().interrupt()
    }
    val wBufMaxSz: Short = 32
    val buf = ByteArray(wBufMaxSz.toInt())
    val bits: Short = lib.GetActiveID32(buf, wBufMaxSz)
    if (bits.toInt() == 0) {
        val errorMessage = """
            
            No id found, Please put card on the reader and make sure it must be configured with the card placed on it.
            """.trimIndent()
        //println(errorMessage)
        return "0"
    } else {
        var bytes_to_read = (bits + 7) / 8
        if (bytes_to_read < 8) {
            bytes_to_read = 8
        }
        //print("\n$bits Bits : ")
        var numOut = arrayOf<String>("","","","","","","","") //initialize array of strings to be populated by buf data
        for (i in bytes_to_read - 1 downTo 0) {
            numOut[i] = String.format("%02x", buf[i])         //populate array with buf data (converted to hexidecimal)
            //System.out.printf("%02X ", buf[i])
        }

        var cardIDString:String = "${numOut[7]}${numOut[6]}${numOut[5]}${numOut[4]}${numOut[3]}${numOut[2]}${numOut[1]}${numOut[0]}" //create a single string of buf data (card ID)
        //var cardID: Int = cardIDString.toInt()
        //println(cardIDString)
        return cardIDString //return the card ID, all other options return "0" which works with the readCard function below
    }
    lib.USBDisconnect()
    //return bits.toInt()
    //return cardID
}


//function used to call card reader for 3 seconds (buffer to allow for slow card tap)
fun readCardOut(Lib: CardDataOut.`SampleClass$Reader`): String{
    var j = 0
    var Bits: String = "0"  //initialize bits variable (will read in data from card reader)
    while (j < 12){         //do this for 12x250ms = 3s
        Bits = getActiveId32Out(Lib)  //get the card data
        if (Bits == "0"){   //if there is no card, increase j and try again
            j++
        }
        else {
            return Bits    //if the card is read, return its ID and break from the while loop
            j = 100
        }
    }
    return Bits    //return whatever is left at the end (either the cardID or 0)
}

class CardDataOut {

    interface `SampleClass$Reader` : Library {
        fun SetDevTypeSrch(var1: Short): Short
        fun usbConnect(): Int
        fun USBDisconnect(): Int
        fun GetLUID(): Int
        fun GetDevCnt(): Short
        fun SetActDev(var1: Short): Short
        val partNumberString: String?
        fun GetVidPidVendorName(): String?
        fun GetActiveID32(var1: ByteArray?, var2: Short): Short
        fun GetLibVersion(var1: IntArray?, var2: IntArray?, var3: IntArray?): Short
    }

    var lib1: `SampleClass$Reader` = Native.loadLibrary("/home/pi/Desktop/pcProxAPI-sdk-7.2.26-Linux-arm.tar_.gz_/lib/32/libhidapi-hidraw.so", `SampleClass$Reader`::class.java)
    var lib: `SampleClass$Reader` = Native.loadLibrary("/home/pi/Desktop/pcProxAPI-sdk-7.2.26-Linux-arm.tar_.gz_/lib/32/libpcProxAPI.so", `SampleClass$Reader`::class.java)

    //call the readCard function and get either and ID or 0 back
    var cardID: String = readCardOut(lib)

    //check if card is in the directory
    var inDirectory: String = checkDirectory(cardID, "Out")

    //add card to the directory
    var added: String = addToDirectory(cardID, inDirectory, "Out")
}