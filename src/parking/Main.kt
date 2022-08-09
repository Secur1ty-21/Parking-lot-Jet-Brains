package parking

import kotlin.math.abs

data class Spot(var registrationNumber: String = "", var color: String = "", var isFree: Boolean = true)

fun main() {
    var isWriteCreate = false // flag for check, when create is write
    lateinit var listSpots: MutableList<Spot>
    var userInput: String
    var command: String // keyword in user input
    var listSize = 0 // size of parking
    while (true) {
        userInput = readln() // get user input
        command = userInput.substringBefore(" ") // get keyword in user input
        if (command == "exit") {
            return
        }
        if (command == "create") {
            listSize = abs(userInput.substringAfterLast(" ").toInt())
            listSpots = MutableList(listSize) { Spot() } // creating parking lot
            println("Created a parking lot with $listSize spots.")
            isWriteCreate = true // mark what parking lot was created
            continue // skip all after this "if"
        }
        if (!isWriteCreate) { // if parking lot is not create, skip all after this "if"
            println("Sorry, a parking lot has not been created.")
            continue
        }
        when (command) {
            "park" -> {
                park(userInput, listSpots, listSize)
            }
            "leave" -> {
                leave(userInput, listSpots, listSize)
            }
            "status" -> {
                status(listSpots, listSize)
            }
            "reg_by_color" -> {
                regByColor(userInput, listSpots, listSize)
            }
            "spot_by_color" -> {
                spotByColor(userInput, listSpots, listSize)
            }
            "spot_by_reg" -> {
                spotByReg(userInput, listSpots, listSize)
            }
            else -> println("Unknown command.")
        }
    }
}

/**
 * Parking car in parking lot.
 */
fun park(userInput: String, listSpots: MutableList<Spot>, listSize: Int) {
    val registration = userInput.substringBeforeLast(" ").substringAfter(" ")
    // Check, what registration number is correct
    for (i in registration) {
        if (i == ' ') {
            println(-1)
        }
    }
    for (i in 0 until listSize) {
        if (listSpots[i].isFree) {
            listSpots[i].isFree = false
            listSpots[i].registrationNumber = registration
            listSpots[i].color = userInput.substringAfterLast(" ")
            println("${listSpots[i].color} car parked in spot ${i + 1}.")
            return
        }
    }
    println("Sorry, the parking lot is full.")
}

/**
 * Clear one spot in parking lot.
 */
fun leave(userInput: String, listSpots: MutableList<Spot>, listSize: Int) {
    val changeSpotForLeave = userInput.substringAfterLast(" ").toInt()
    if (changeSpotForLeave in 0 until listSize) {
        if (listSpots[changeSpotForLeave - 1].isFree) {
            println("There is no car in spot $changeSpotForLeave.")
        } else {
            println("Spot $changeSpotForLeave is free.")
            listSpots[changeSpotForLeave - 1].isFree = true
        }
    } else {
        println(-1)
    }
}

/**
 * List of occupied parking spaces
 */
fun status(listSpots: MutableList<Spot>, listSize: Int) {
    var checkParkingOnEmpty = true
    for (i in 0 until listSize) {
        if (!listSpots[i].isFree) {
            println("${i + 1} ${listSpots[i].registrationNumber} ${listSpots[i].color}")
            checkParkingOnEmpty = false
        }
    }
    if (checkParkingOnEmpty) {
        println("Parking lot is empty.")
    }
}

/**
 *  List of registration numbers with the same color
 */
fun regByColor(userInput: String, listSpots: MutableList<Spot>, listSize: Int) {
    val color = userInput.substringAfterLast(" ")
    var registrationNumbers = ""
    for (i in 0 until listSize) {
        if (!listSpots[i].isFree && listSpots[i].color.equals(color, true)) {
            registrationNumbers += listSpots[i].registrationNumber + ", "
        }
    }
    if (registrationNumbers.isEmpty()) {
        println("No cars with color $color were found.")
    } else {
        println(registrationNumbers.substringBeforeLast(","))
    }
}

/**
 * List of spots with the same color
 */
fun spotByColor(userInput: String, listSpots: MutableList<Spot>, listSize: Int) {
    val color = userInput.substringAfterLast(" ")
    var registrationNumbers = ""
    for (i in 0 until listSize) {
        if (!listSpots[i].isFree && listSpots[i].color.equals(color, true)) {
            registrationNumbers += "${i + 1}, "
        }
    }
    if (registrationNumbers.isEmpty()) {
        println("No cars with color $color were found.")
    } else {
        println(registrationNumbers.substringBeforeLast(","))
    }
}

/**
 * Find the spot of the car by registration number
 */
fun spotByReg(userInput: String, listSpots: MutableList<Spot>, listSize: Int) {
    val registrationNumber = userInput.substringAfterLast(" ")
    for (i in 0 until listSize) {
        if (!listSpots[i].isFree && listSpots[i].registrationNumber == registrationNumber) {
            println(i + 1)
            return
        }
    }
    println("No cars with registration number $registrationNumber were found.")
}