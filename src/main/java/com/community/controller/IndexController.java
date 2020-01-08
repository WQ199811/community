package com.community.controller;

import com.community.Service.QuestionService;
import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                         Model model){
       Cookie[] cookies = request.getCookies();
       if(cookies != null&&cookies.length != 0){
           for (Cookie cookie:cookies){
               if (cookie.getName().equals("token")){
                   String token = cookie.getValue();
                   User user = userMapper.findByToken(token);
                   if(user != null){
                       request.getSession().setAttribute("user",user);
                   }
                   break;
               }
           }
       }
        List<QuestionDTO> questions = questionService.questionsAll();
        model.addAttribute("questions",questions);
        System.out.println(questions);
        return "index";
    }
}
