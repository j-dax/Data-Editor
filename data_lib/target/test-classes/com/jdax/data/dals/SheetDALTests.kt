package com.jdax.data.dals

import com.jdax.data.dals.SheetDAL
import com.jdax.data.window.FullWindow
import java.nio.file.Path
import java.nio.file.Paths
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import org.testng.Assert

@Test
class SheetDALTests {

    val excelPathString: String = "src/test/resources/Financial Sample.xlsx"

    @DataProvider(name = "excelPaths")
    public fun excelPathsProvider(): Array<Array<Any>> {
        return arrayOf(
            arrayOf(Paths.get(excelPathString), "Sheet1")
        )
    }

    @Test(dataProvider = "excelPaths", groups = arrayOf("excelGroup"))
    public fun verifyPath(excelPath: Path, sheetName: String) {
        Assert.assertTrue(excelPath.toFile().exists())
    }

    @Test(dataProvider = "excelPaths", groups = arrayOf("excelGroup"), dependsOnMethods = arrayOf("verifyPath"))
    public fun verifySheet(excelPath: Path, sheetName: String) {
        WorkbookFactory.create(excelPath.toFile()).use {
            it.getSheet(sheetName) ?: Assert.fail("sheet does not exist")
        }
    }

    @Test(dataProvider = "excelPaths", groups = arrayOf("excelGroup"), dependsOnMethods = arrayOf("verifySheet"))
    public fun getWindowTest(excelPath: Path, sheetName: String) {
        val window: FullWindow = SheetDAL(excelPath, sheetName).getFullWindow()
        Assert.assertEquals(window.vertical().count(), 701)
        Assert.assertEquals(window.horizontal().count(), 17)
    }

}