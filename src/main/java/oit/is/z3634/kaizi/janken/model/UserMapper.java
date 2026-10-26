package oit.is.z3634.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  // ユーザー情報をすべて取得する
  @Select("SELECT * FROM users;")
  ArrayList<User> selectAllUser();

  // ユーザーIDを使用して、ユーザー情報を1件取得する
  @Select("SELECT * FROM users WHERE id = #{id};")
  User selectUserById(int id);
}
