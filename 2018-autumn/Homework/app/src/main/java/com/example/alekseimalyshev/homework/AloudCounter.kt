package com.example.alekseimalyshev.homework

import android.content.Context
import android.content.res.Resources

/**
 * Created by alekseimalyshev on 29.10.2018.
 */
class AloudCounter (val context: Context){

    val mContext = context


    fun sayAloud(number: Int) : String {
        if (number == 1000) {
            return context.resources.getString(R.string.thousand)
        }
        else {
            val hundreds: Int = number / 100
            val dozens: Int = number % 100
            val decades: Int = dozens / 10
            val units: Int = number % 10

            var result: String = ""


            result += sayHundredsAloud(hundreds)
            if ((decades != 0 || units != 0) && hundreds != 0) {
                result += " "
            }
            if (decades == 1) {
                result += sayDozensAloud(dozens)
                return result
            } else {
                result += sayDecadesAloud(decades)
                if (units != 0 && decades != 0) {
                    result += " "
                }
                result += sayUnitsAloud(units)
                return result
            }
        }
    }

    fun sayHundredsAloud(hundreds: Int) : String {
        if (hundreds == 0) {
            return ""
        }
        else {
            val hundredsArray: Array<String> = mContext.resources.getStringArray(R.array.hundreds)
            return hundredsArray[hundreds - 1]
        }
    }

    fun sayDecadesAloud(decades: Int) : String {
        if (decades == 0) {
            return ""
        }
        else {
            val decadesArray: Array<String> = mContext.resources.getStringArray(R.array.decades)
            return decadesArray[decades - 2]
        }
    }

    fun sayDozensAloud(dozens: Int) : String {
        val dozensArray: Array<String> = mContext.resources.getStringArray(R.array.dozens)

        return dozensArray[dozens - 10]
    }

    fun sayUnitsAloud(units: Int) : String {
        if (units == 0) {
            return ""
        }
        else {
            val unitsArray: Array<String> = mContext.resources.getStringArray(R.array.units)
            return unitsArray[units - 1]
        }
    }
}