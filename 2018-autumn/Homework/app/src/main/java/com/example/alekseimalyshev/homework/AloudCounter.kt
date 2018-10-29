package com.example.alekseimalyshev.homework

import android.content.Context
import android.content.res.Resources

/**
 * This class provides a string representation in Russian for any natural
 * number in range [1..1000]. Probably, it should have been just a collection of functions,
 * but creating a class to solve this problem sounds so cool (you know, OOP is so cool).
 * Moreover, it is rather overloaded since it doesn't emply an algorithm with the minimum
 * amount of the hardcoded strings, but I did this homework day before deadline so I didn't
 * want to complicate a task for me.
 */
class AloudCounter (val context: Context){

    // A variable which stores Android context such that class can access resources of the app
    val mContext = context

    /**
     * Returns a Russian string representation of a natural number in range [1..1000]
     *
     * @param number An integer number (should be in range [1..1000])
     * @return string result A Russian string representation of a number
     */
    fun sayAloud(number: Int) : String {
        // Check if we have reached the maximum of proposed range
        if (number == 1000) {
            // Return the hardcoded value
            return context.resources.getString(R.string.thousand)
        }
        else {
            // Split number into four numbers: hundreds, decades and units are rather obvious
            // while dozens fits the case of the following structure *{10..19}
            val hundreds: Int = number / 100
            val dozens: Int = number % 100
            val decades: Int = dozens / 10
            val units: Int = number % 10

            var result: String = ""

            // We add representation of hundreds to our result
            result += sayHundredsAloud(hundreds)

            // If we added something and there are consequent words, we add a space
            if ((decades != 0 || units != 0) && hundreds != 0) {
                result += " "
            }
            // If we are stuck into the case of *{10..19}, we should handle it correspondingly
            if (decades == 1) {
                // Just print representation of a number in range [10..19]
                result += sayDozensAloud(dozens)
                return result
            } else {
                // Otherwise, we print representations of decades and units separately
                result += sayDecadesAloud(decades)


                if (units != 0 && decades != 0) {
                    result += " "
                }
                result += sayUnitsAloud(units)
                return result
            }
        }
    }

    /**
     * Returns a Russian string representation of given number hundreds
     *
     * @param hundreds An integer number of hundreds in the given number
     * @return string representation of given number hundreds
     */
    fun sayHundredsAloud(hundreds: Int) : String {
        // If there are no hundreds, we return nothing
        if (hundreds == 0) {
            return ""
        }
        // Otherwise we return something
        else {
            val hundredsArray: Array<String> = mContext.resources.getStringArray(R.array.hundreds)
            return hundredsArray[hundreds - 1]
        }
    }

    /**
     * Returns a Russian string representation of given number decades
     *
     * @param decades An integer number of hundreds in the given number
     * @return string representation of given number decades
     */
    fun sayDecadesAloud(decades: Int) : String {
        // If there are no decades, we return nothing
        if (decades == 0) {
            return ""
        }
        // Otherwise we return something
        else {
            val decadesArray: Array<String> = mContext.resources.getStringArray(R.array.decades)
            return decadesArray[decades - 2]
        }
    }

    /**
     * Returns a Russian string representation of given number dozens
     *
     * @param dozens An integer number of dozens in the given number
     * @return string representation of given number dozens
     */
    fun sayDozensAloud(dozens: Int) : String {
        // Just return the hardcoded value
        val dozensArray: Array<String> = mContext.resources.getStringArray(R.array.dozens)

        return dozensArray[dozens - 10]
    }

    /**
     * Returns a Russian string representation of given number units
     *
     * @param units An integer number of units in the given number
     * @return string representation of given number units
     */
    fun sayUnitsAloud(units: Int) : String {
        // If there are no decades, we return nothing
        if (units == 0) {
            return ""
        }
        // Otherwise we return something
        else {
            val unitsArray: Array<String> = mContext.resources.getStringArray(R.array.units)
            return unitsArray[units - 1]
        }
    }
}