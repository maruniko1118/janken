package oit.is.z3634.kaizi.janken.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  // アクティブな対戦情報を取得する
  @Select("SELECT * FROM matchinfo WHERE isActive = true;")
  java.util.ArrayList<MatchInfo> selectActiveMatchInfo();

  // 対戦情報を追加する
  @Insert("INSERT INTO matchinfo(user1, user2, user1Hand, isActive) VALUES(#{user1}, #{user2}, #{user1Hand}, #{isActive});")
  void insertMatchInfo(MatchInfo matchInfo);

  // 自分の名前が含まれるアクティブな対戦情報
  @Select("SELECT * FROM matchinfo WHERE (user1 = #{id} OR user2 = #{id}) AND isActive = true;")
  java.util.ArrayList<MatchInfo> selectActiveMatchInfoByUsername(int id);

  // 対戦情報のアクティブ状態を更新する
  @Update("UPDATE matchinfo SET isActive = #{isActive} WHERE id = #{id};")
  void updateMatchInfo(MatchInfo matchInfo);
}
