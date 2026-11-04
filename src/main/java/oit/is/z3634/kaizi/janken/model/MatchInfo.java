package oit.is.z3634.kaizi.janken.model;

public class MatchInfo {
  private Integer id;
  private Integer user1;
  private Integer user2;
  private String user1Hand;
  private Boolean isActive;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUser1() {
    return this.user1;
  }

  public void setUser1(Integer user1) {
    this.user1 = user1;
  }

  public Integer getUser2() {
    return this.user2;
  }

  public void setUser2(Integer user2) {
    this.user2 = user2;
  }

  public String getUser1Hand() {
    return this.user1Hand;
  }

  public void setUser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  public Boolean getIsActive() {
    return this.isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }
}
