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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(HttpServletRequest request) {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Integer id,
                            HttpServletRequest request,
                            Model model
    ) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        questionService.createrOrUpdate(question);
        model.addAttribute("success","1");
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        QuestionDTO question = questionService.questionsById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);
        return "publish";
    }
}
