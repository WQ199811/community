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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class profileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                           @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                           Model model,
                           HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        else if("repies".equals(action)){
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }
        PageHelper.startPage(pn, 5);
        List<QuestionDTO> questions = questionService.questionsByCreator(user.getId());
        PageHelper.startPage(pn, 5);
        List<Question> questions1 = questionMapper.questionsByCreator(user.getId());
        PageInfo<Question> questionsPage1 = new PageInfo<Question>(questions1, 5);

        model.addAttribute("questions", questions);
        model.addAttribute("pages", questionsPage1.getPages());//总页码
        model.addAttribute("pageNum", questionsPage1.getPageNum());//当前页
        model.addAttribute("navigatepageNums", questionsPage1.getNavigatepageNums());//格式
        return "profile";
    }
}
