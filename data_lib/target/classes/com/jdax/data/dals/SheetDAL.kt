package com.jdax.data.dals

import com.jdax.data.dals.IDAL
import com.jdax.data.window.AbstractWindow
import com.jdax.data.window.DataWindow
import com.jdax.data.window.FullWindow
import com.jdax.data.utils.coerceToString
import java.nio.file.Path
import java.io.File
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy
import org.apache.poi.ss.usermodel.WorkbookFactory

public fun getFullWindow(workbookPath: Path, sheetName: String): FullWindow {
    return getFullWindow(workbookPath.toFile(), sheetName)
}

private fun getFullWindow(workbookFile: File, sheetName: String): FullWindow {
    WorkbookFactory.create(workbookFile).use {
        return getFullWindow(it.getSheet(sheetName))
    }
}

private fun getFullWindow(sheet: Sheet): FullWindow {
    val row0: Row = sheet.iterator().next() // allow this to fail if there isn't a single row
    return FullWindow(0, row0.lastCellNum.toInt(), 0, sheet.lastRowNum)
}

public fun readWindow(
        workbookPath: Path,
        sheetName: String,
        window: AbstractWindow
): List<List<String>> {
    return readWindow(workbookPath.toFile(), sheetName, window)
}

private fun readWindow(
        workbookFile: File,
        sheetName: String,
        window: AbstractWindow
): List<List<String>> {
    WorkbookFactory.create(workbookFile).use {
        return readWindow(it.getSheet(sheetName), window)
    }
}

private fun readWindow(sheet: Sheet, window: AbstractWindow): List<List<String>> {
    val list: List<MutableList<String>> = List(window.height()) { mutableListOf() }
    for (rownum in window.vertical()) {
        val row: Row = sheet.getRow(rownum)
        for (cellnum in window.horizontal()) {
            list[window.y1 - rownum][window.x1 - cellnum] =
                    row.getCell(cellnum, MissingCellPolicy.CREATE_NULL_AS_BLANK).coerceToString()
        }
    }
    return list
}

public class SheetDAL constructor(val workbookPath: Path, val sheetName: String): IDAL {

    override public fun readWindow(window: AbstractWindow): DataWindow {
        return DataWindow(readWindow(workbookPath, sheetName, window))
    }

    override public fun getFullWindow(): FullWindow {
        return getFullWindow(workbookPath, sheetName)
    }

}

