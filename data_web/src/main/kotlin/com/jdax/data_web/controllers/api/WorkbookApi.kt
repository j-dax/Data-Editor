package com.jdax.data_web.controllers.api

// import com.jdax.data_web.services.getSlidingWindow

import com.jdax.data_web.services.*
import com.jdax.data_web.dals.SlidingWindow
import java.nio.file.Path
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/api/workbook")
class WorkbookApi {

    @GetMapping("/list", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody
    fun listWorkbooks(): String {
        return "{".plus(
                        getAllResourcePaths()
                                .map { "\"${it.key}\" : \"${it.value.name}\"" }
                                .joinToString(",")
                )
                .plus("}")
    }

    @GetMapping("/list/sheets", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody
    fun listWorkbookSheets(): String {
        return "{".plus(
                        getAllResourcePaths()
                                .map { "\"${it.key}\" : ${getSheetNames(it.value)}" }
                                .joinToString(",")
                )
                .plus("}")
    }

    @PostMapping("/new")
    fun uploadWorkbook(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        storeResource(file)
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully uploaded")
    }

    @GetMapping("/{wbKey}/{sheetName}/window")
    @ResponseBody
    fun getFullSheetWindow(
            @PathVariable("wbKey") wbKey: String,
            @PathVariable("sheetName") sheetName: String
    ): String {
        val workbookPath: Path = getResource(wbKey)
        return getSlidingWindow(workbookPath, sheetName).toString()
    }

    @PostMapping("/{wbKey}/{sheetName}/window", consumes=arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun setSheetWindow(
        @PathVariable("wbKey") wbKey: String,
        @PathVariable("sheetName") sheetName: String,
        @RequestBody body: Map<String, String>
    ): ResponseEntity<String> {
        println("Handling new request")
        println("wbKey: ${wbKey}")
        println("sheetName: ${sheetName}")
        println("body: ${body}")
        return ResponseEntity.status(HttpStatus.OK).body("Successfully updated. Also Kapp")
    }
}
