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
            /**questionDTO.setId(question.getId());
            questionDTO.setTitle(question.getTitle());
            questionDTO.setTag(question.getTag());
            questionDTO.setCreator(question.getCreator());
            questionDTO.setComment_count(question.getComment_count());
            questionDTO.setDescription(question.getDescription());
            questionDTO.setGmt_create(question.getGmt_create());
            questionDTO.setGmt_modified(question.getGmt_modified());
            questionDTO.setLike_count(question.getLike_count());
            questionDTO.setView_count(question.getView_count());**/
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
      return questionDTOs;
    }
}
