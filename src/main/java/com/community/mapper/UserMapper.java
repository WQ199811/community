package com.community.mapper;

import com.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
    void insert(User user);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmt_modified},avatar_url=#{avatar_url} where account_id=#{account_id}")
    void update(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{creator}")
    User findById(Integer creator);

    @Select("select * from user where account_id = #{account_id}")
    User findByAccountId(String account_id);
}
