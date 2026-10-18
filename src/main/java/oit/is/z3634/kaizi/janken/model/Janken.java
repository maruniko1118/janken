package oit.is.z3634.kaizi.janken.model;

import java.util.List;
import java.util.Random;

public class Janken {

  // メンバ一覧
  private String hand;
  private String result;

  // コンストラクタ (CPU用)
  public Janken() {
    this.hand = setRandomHand();
    this.result = null;
  }

  // コンストラクタ (Player用)
  public Janken(String hand) {
    this.hand = hand;
    this.result = null;
  }

  // ランダムな手をセットする
  private String setRandomHand() {
    List<String> handList = List.of("グー", "チョキ", "パー");
    Random rand = new Random();

    return handList.get(rand.nextInt(handList.size()));
  }

  // 勝ち負けの判定
  public void judge(String enemyHand) {
    String result;
    switch (enemyHand) {
      case "グー":
        result = rockJudge();
        break;
      case "チョキ":
        result = scissorsJudge();
        break;
      case "パー":
        result = paperJudge();
        break;
      default:
        result = null; // フロントエンドで "result == null" のとき結果が表示されない(仕様)。
        break;
    }
    this.result = result;
  }

  // 相手がグー
  private String rockJudge() {
    String result;
    switch (this.hand) {
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
  private String scissorsJudge() {
    String result;
    switch (this.hand) {
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
  private String paperJudge() {
    String result;
    switch (this.hand) {
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

  public String getHand() {
    return this.hand;
  }

  public String getResult() {
    return this.result;
  }
}
