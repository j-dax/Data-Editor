package com.jdax.data.utils

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.xssf.usermodel.XSSFCell

public fun Cell.coerceToString(): String {
    if (this is XSSFCell) {
        return this.toString()
    } else {
        return DataFormatter().formatCellValue(this)
    }
}