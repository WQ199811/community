package com.community.mapper;

import com.community.dto.QuestionDTO;
import com.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question")
    List<Question> questionsAll();

    @Select("select * from question where creator = #{creator}")
    List<Question> questionsByCreator(Integer creator);

    @Select("select * from question where id = #{id}")
    Question questionsById(Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmt_modified},tag=#{tag} where id=#{id}")

    void questionsUpdateById(Question question);
}
