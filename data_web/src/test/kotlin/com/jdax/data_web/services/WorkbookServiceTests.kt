package com.jdax.data_web.services

import com.jdax.data_web.dals.SlidingWindow
import java.io.File
import java.nio.file.Paths
import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.net.URI
import org.springframework.boot.test.context.SpringBootTest
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import org.testng.Assert

@SpringBootTest
class WorkbookServiceTests {

    val testBaseFileName: String = "Financial Sample.xlsx"
    val testExcelFileString: String = "/${testBaseFileName}"
    val testExcelURI: URI = this.javaClass.getResource(testExcelFileString).toURI()
    val sheetNames: List<String> = listOf("Sheet1")
    val sheet1Headers: List<String> =
            listOf(
                    "Segment",
                    "Country",
                    "Product",
                    "Discount Band",
                    "Units Sold",
                    "Manufacturing Price",
                    "Sale Price",
                    "Gross Sales",
                    "Discounts",
                    "Sales",
                    "COGS",
                    "Profit",
                    "Date",
                    "Month Number",
                    "Month Name",
                    "Year"
            )

    @DataProvider(name = "prepareFile")
    public fun prepareFileProvider(): Array<Array<Path>> {
        val path: Path = Files.copy(Paths.get(testExcelURI), Files.createTempFile("Financial Sample", "xlsx"), StandardCopyOption.REPLACE_EXISTING)
        path.toFile().deleteOnExit()
        return arrayOf(arrayOf(path))
    }

    @DataProvider(name = "prepareSheet")
    public fun prepareSheetProvider(): Array<Array<Any>> {
        val path: Path = Files.copy(Paths.get(testExcelURI), Files.createTempFile("Financial Sample", "xlsx"), StandardCopyOption.REPLACE_EXISTING)
        path.toFile().deleteOnExit()
        return arrayOf(arrayOf(path, "Sheet1"))
    }

    @Test(dataProvider = "prepareSheet")
    public fun getSheetNamesTest(path: Path, sheetName: String) {
        val testSheetNames: List<String> = getSheetNames(path.toFile())
        Assert.assertEquals(testSheetNames.size, 1)
        Assert.assertEquals(sheetName, testSheetNames.get(0))
    }

    @Test(dataProvider = "prepareSheet")
    public fun getFullWindowTest(path: Path, sheetName: String) {
        val window: SlidingWindow = getSlidingWindow(path, sheetName)
        Assert.assertEquals(window.width(), 16)
        Assert.assertEquals(window.height(), 700)
    }

    @Test(dataProvider = "prepareSheet")
    public fun getFillWindowContentTest(path: Path, sheetName: String) {
        val window: SlidingWindow = getSlidingWindow(path, sheetName)// may be too large
        val values: List<List<String>> = getContentInWindow(path, sheetName, window)
        Assert.assertEquals(values.size, 701) // 0 to 700
        Assert.assertEquals(values.get(0).size, 17) // 0 to 16
    }
}