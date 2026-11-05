package oit.is.z3634.kaizi.janken.model;

import java.util.List;
import java.util.Random;

public class Janken {

  // ランダムな手を取得する
  public String getRandomHand() {
    List<String> handList = List.of("グー", "チョキ", "パー");
    Random rand = new Random();

    return handList.get(rand.nextInt(handList.size()));
  }

  // 勝ち負けの判定
  public String judge(String player, String opponent) {
    String result;
    switch (player) {
      case "グー":
        result = rockJudge(opponent);
        break;
      case "チョキ":
        result = scissorsJudge(opponent);
        break;
      case "パー":
        result = paperJudge(opponent);
        break;
      default:
        result = null; // フロントエンドで "result == null" のとき結果が表示されない(仕様)。
        break;
    }
    return result;
  }

  // 相手がグー
  private String rockJudge(String opponent) {
    String result;
    switch (opponent) {
      case "グー":
        result = "あいこ";
        break;
      case "チョキ":
        result = "負け";
        break;
      case "パー":
        result = "勝ち";
        break;
      default:
        result = null; // フロントエンドで "result == null" のとき結果が表示されない(仕様)。
        break;
    }
    return result;
  }

  // 相手がチョキ
  private String scissorsJudge(String opponent) {
    String result;
    switch (opponent) {
      case "グー":
        result = "勝ち";
        break;
      case "チョキ":
        result = "あいこ";
        break;
      case "パー":
        result = "負け";
        break;
      default:
        result = null; // フロントエンドで "result == null" のとき結果が表示されない(仕様)。
        break;
    }
    return result;
  }

  // 相手がパー
  private String paperJudge(String opponent) {
    String result;
    switch (opponent) {
      case "グー":
        result = "負け";
        break;
      case "チョキ":
        result = "勝ち";
        break;
      case "パー":
        result = "あいこ";
        break;
      default:
        result = null; // フロントエンドで "result == null" のとき結果が表示されない(仕様)。
        break;
    }
    return result;
  }
}
