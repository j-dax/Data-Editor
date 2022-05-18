package com.jdax.data_web.routers

// import com.jdax.data_web.services.getSlidingWindow
import com.jdax.data_web.services.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/workbook")
class WorkbookRouter {
    @GetMapping("/save")
    fun saveWorkbook(): String {
        return "form"
    }

    @GetMapping("")
    fun viewApp(request: HttpServletRequest): ModelAndView {
        val result: ModelAndView = ModelAndView()
        result.addObject("resources", request.getContextPath() + "/resources/static")
        result.setViewName("app")
        return result
    }
}
