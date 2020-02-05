package com.jgji.spring.domain.user.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jgji.spring.domain.user.model.User;
import com.jgji.spring.domain.user.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    
    @GetMapping("/user/create")
    public String initCreationForm(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);
        
        return "thymeleaf/createUserForm";
    }
    
    @PostMapping("/user/create")
    public String processCreationForm(@Valid User user, BindingResult result) {
        if (userService.isExistName(user.getUsername())) {
            result.rejectValue("username", "username", "이미 존재하는 아이디 입니다.");
        }
        
        if (result.hasErrors()) {
            return "thymeleaf/createUserForm";
        }
        
        this.userService.save(user);
        return "redirect:/";
    }
    
    @GetMapping("/user/profile")
    public ModelAndView showUserProfileForm() {
        ModelAndView mav = new ModelAndView("thymeleaf/viewUserProfileForm");
        User user = userService.getUserByUserName(userService.getCurrentUserName());
        System.out.println(user.getUsername());
        mav.addObject("user", user);
        
        return mav;
    }
}
