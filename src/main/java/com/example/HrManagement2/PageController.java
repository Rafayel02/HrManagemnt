package com.example.HrManagement2;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PageController {

    private final UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/login")
    public ModelAndView getLoginFailPage(@RequestParam(required = false) boolean error) {
        ModelAndView mv = new ModelAndView("login-page");
        mv.addObject("error", error);
        return mv;
    }

    @GetMapping("/a")
    public String a() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null));
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register-page";
    }

    @PostMapping("/register")
    public String getBack(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println(username + ": " + password);
        HR newHR = userService.register(username, password);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(newHR, password));
        return "redirect:/";
    }

}
