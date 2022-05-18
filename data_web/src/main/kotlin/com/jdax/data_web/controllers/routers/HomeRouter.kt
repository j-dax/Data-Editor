package com.jdax.data_web.routers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomeRouter {
    @GetMapping("/")
    @ResponseBody
    fun home(): String {
        return "OK"
    }

    @GetMapping("/test")
    fun test(): String {
        return "test"
    }
}