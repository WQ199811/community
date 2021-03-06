package com.community.controller;

import com.community.Service.QuestionService;
import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "pn", defaultValue = "1") Integer pn
    ) {

        PageHelper.startPage(pn, 5);
        List<QuestionDTO> questions = questionService.questionsAll();
        PageHelper.startPage(pn, 5);
        List<Question> questions1 = questionMapper.questionsAll();
        PageInfo<Question> questionsPage1 = new PageInfo<Question>(questions1, 5);


        model.addAttribute("questions", questions);
        model.addAttribute("pages", questionsPage1.getPages());//总页码
        model.addAttribute("pageNum", questionsPage1.getPageNum());//当前页
        model.addAttribute("navigatepageNums", questionsPage1.getNavigatepageNums());//格式


        return "index";
    }


}
