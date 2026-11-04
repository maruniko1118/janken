package oit.is.z3634.kaizi.janken.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT * FROM matchinfo WHERE isActive = true;")
  java.util.ArrayList<MatchInfo> selectActiveMatchInfo();

  @Insert("INSERT INTO matchinfo(user1, user2, user1Hand, isActive) VALUES(#{user1}, #{user2}, #{user1Hand}, #{isActive});")
  void insertMatchInfo(MatchInfo matchInfo);
}
