package oit.is.z3634.kaizi.janken.model;

public class Janken {

  // メンバ一覧
  String hand;
  String result;

  // コンストラクタ
  public Janken(String hand) {
    this.hand = hand;
    this.result = judge();
  }

  // CPUの手はグーで固定
  private String judge() {
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

  public String getHand() {
    return this.hand;
  }

  public String getResult() {
    return this.result;
  }
}
