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

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths

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

class CSVData(private val filePath: String) {
    // read the file
    private val reader = Files.newBufferedReader(Paths.get(filePath))

    // parse the file into csv values
    private val csvParser = CSVParser(
        reader, CSVFormat.DEFAULT
            .withHeader(
                "Time",
                "Activity",
                "Panel",
                "Panel Location",
                "Mod",
                "Door",
                "Door Location",
                "PIN",
                "Card #",
                "Last Name",
                "First Name"
            )
            .withIgnoreHeaderCase()
            .withTrim()
    )

    // "Time","Activity","Panel","Panel Location","Mod","Door","Door Location","PIN","Card #","Last Name","First Name"

    private val tapEvents = mutableListOf<TapEvent>()

    fun parse() {
        for (csvRecord in csvParser) {
            if (csvRecord.recordNumber > 14) {
                tapEvents.add(
                    TapEvent(
                        csvRecord.get("Time"),
                        csvRecord.get("Activity"),
                        csvRecord.get("Panel"),
                        csvRecord.get("Panel Location"),
                        csvRecord.get("Mod"),
                        csvRecord.get("Door"),
                        csvRecord.get("Door Location"),
                        csvRecord.get("PIN"),
                        csvRecord.get("Card #"),
                        csvRecord.get("Last Name"),
                        csvRecord.get("First Name")
                    )
                )
            }
        }
    }

    fun eventsList(): String {
        var toRet: String = ""
        for (tapEvent in tapEvents) {
            toRet += "------------------------------------\n"
            toRet += "Name: ${tapEvent.firstName} ${tapEvent.lastName}\n"
            toRet += "Tap time: ${tapEvent.time}\n"
            toRet += "Location: ${tapEvent.doorLocation}\n"
            toRet += "------------------------------------\n"
        }
        return toRet
    }
}