package com.community.Service;

import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public List<QuestionDTO> questionsAll(){
        List<Question> questions = questionMapper.questionsAll();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
      return questionDTOs;
    }
    public List<QuestionDTO> questionsByCreator(Integer creator) {
        List<Question> questions = questionMapper.questionsByCreator(creator);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
    public QuestionDTO questionsById(Integer id) {
        Question question = questionMapper.questionsById(id);
        User user = userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }


    public void createrOrUpdate(Question question) {
        if (question.getId()==null){
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            questionMapper.create(question);
        }else{
            question.setGmt_modified(System.currentTimeMillis());
            questionMapper.questionsUpdateById(question);
        }
    }
}
