package com.jdax.data_web.services

import com.jdax.data.window.SlidingWindow
import com.jdax.data.utils.coerceToString
import com.jdax.data.utils.logger
import com.jdax.data_web.services.SavedFileWrapper
import java.io.File
import java.nio.file.Path
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.ss.usermodel.Workbook
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager
// import org.slf4j.Logger

private val lazyLogger: Lazy<Logger> = lazy { LogManager.getLogger() }

/** This service provides transactional reads and writes on a sliding window within the workbook */
public fun getSheetNames(fileWrapper: SavedFileWrapper): List<String> {
    return getSheetNames(fileWrapper.path.toFile())
}

public fun getSheetNames(file: File): List<String> {
    val list: MutableList<String> = mutableListOf()
    WorkbookFactory.create(file).use {
        getSheetNames(it, list)
    }
    return list
}

public fun getSheetNames(workbook: Workbook, list: MutableList<String>) {
    workbook.sheetIterator().forEach { list.add(it.sheetName) }
}

public fun getSlidingWindow(workbookPath: Path, sheetName: String, stepSize: Int): SlidingWindow {
    return getSlidingWindow(workbookPath.toFile(), sheetName, stepSize)
}

fun getSlidingWindow(workbookFile: File, sheetName: String, stepSize: Int): SlidingWindow {
    WorkbookFactory.create(workbookFile).use {
        return getSlidingWindow(it.getSheet(sheetName), stepSize)
    }
}

fun getSlidingWindow(sheet: Sheet, stepSize: Int): SlidingWindow {
    // assume the top row contains the maximum number of cells that the rest of the sheet will have
    val row: Row = sheet.getRow(0)
    return SlidingWindow(0, row.lastCellNum.toInt(), 0, sheet.lastRowNum, stepSize)
}

public fun getContentInWindow(
        workbookPath: Path,
        sheetName: String,
        window: SlidingWindow
): List<List<String>> {
    return getContentInWindow(workbookPath.toFile(), sheetName, window)
}

fun getContentInWindow(
        workbookFile: File,
        sheetName: String,
        window: SlidingWindow
): List<List<String>> {
    WorkbookFactory.create(workbookFile).use {
        return getContentInWindow(it.getSheet(sheetName), window)
    }
}

fun getContentInWindow(sheet: Sheet, window: SlidingWindow): List<List<String>> {
    return window.vertical().map({ i -> sheet.getRow(i).getRowContents(window.horizontal()) })
}

fun Row.getRowContents(range: IntRange): List<String> {
    return range.map({ i: Int -> this.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK).coerceToString() })
}
