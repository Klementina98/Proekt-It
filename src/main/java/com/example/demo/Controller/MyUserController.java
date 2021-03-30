package com.example.demo.Controller;

import com.example.demo.Exceptions.InvalidUsernameException;
import com.example.demo.Model.MyUser;
import com.example.demo.Model.Role;
import com.example.demo.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MyUserController {

    private final UserService userService;

    public MyUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("register")
    public String getRegisterPage(){
        return "Register";
    }
    @GetMapping("login")
    public String getLogInPage(){
        return "LogIn";
    }

    @GetMapping("logoutUser")
    public String getLogoOutPage(HttpServletRequest request){
        request.getSession().invalidate();
        System.out.println(request.getRemoteUser());
        return "redirect:/items";
    }

    @PostMapping("register")
    public String register(@RequestParam String username, @RequestParam String password){
        //sekoj obichen user
        Role role = Role.ROLE_USER;
        userService.create(username,password,role);
        return "redirect:/login";
    }

    @PostMapping("login")
    public String login(HttpServletRequest request,
                        @RequestParam String username, @RequestParam String password) throws InvalidUsernameException {
        MyUser user = userService.login(username,password);
        request.getSession().setAttribute("user", user);
        //ako e uspeshno
        return "redirect:/items";
    }


}
