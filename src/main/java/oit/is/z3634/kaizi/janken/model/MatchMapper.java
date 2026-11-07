package oit.is.z3634.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * FROM matches;")
  ArrayList<Match> selectAllMatch();

  @Insert("INSERT INTO matches(user1, user2, user1Hand, user2Hand, isActive) VALUES(#{user1}, #{user2}, #{user1Hand}, #{user2Hand}, #{isActive});")
  void insertMatch(Match match);

  @Select("SELECT * FROM matches WHERE isActive = true;")
  ArrayList<Match> selectActiveMatches();

  @Update("UPDATE matches SET isActive = #{isActive} WHERE id = #{id};")
  void updateMatch(Match match);
}
