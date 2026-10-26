package oit.is.z3634.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3634.kaizi.janken.model.Entry;
import oit.is.z3634.kaizi.janken.model.Janken;
import oit.is.z3634.kaizi.janken.model.Match;
import oit.is.z3634.kaizi.janken.model.MatchMapper;
import oit.is.z3634.kaizi.janken.model.User;
import oit.is.z3634.kaizi.janken.model.UserMapper;

// HTTPリクエストがあったときに呼び出されるクラス
@Controller
@RequestMapping("/janken")
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper UserMapper;

  @Autowired
  private MatchMapper MatchMapper;

  // GETで "/janken" にアクセスがあった場合
  // ユーザー名を取得せず、janken.htmlを表示
  @GetMapping()
  public String janken(Principal prin, ModelMap model) {
    ArrayList<User> users = UserMapper.selectAllUser();
    ArrayList<Match> matches = MatchMapper.selectAllMatch();
    String username = prin.getName();
    this.entry.addUser(username);
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    model.addAttribute("username", username);
    model.addAttribute("entry", entry);
    return "janken.html";
  }

  // POSTで "/janken" にアクセスがあった場合
  // ユーザー名を取得し、janken.htmlを表示
  @PostMapping()
  public String janken(@RequestParam String username, ModelMap model) {
    model.addAttribute("username", username);
    return "janken.html";
  }

  // 対戦ページへ移動
  @GetMapping("match")
  public String match(@RequestParam int id, ModelMap model) {
    User user = UserMapper.selectUserById(id);
    model.addAttribute("user", user);
    return "match.html";
  }

  // 対戦
  @GetMapping("fight")
  public String fight(Principal prin, @RequestParam int id, @RequestParam String hand, ModelMap model) {
    // じゃんけんの処理
    Janken playerJanken = new Janken(hand);
    Janken opponentJanken = new Janken("グー");
    playerJanken.judge(opponentJanken.getHand());
    model.addAttribute("playerHand", playerJanken.getHand());
    model.addAttribute("opponentHand", opponentJanken.getHand());
    model.addAttribute("result", playerJanken.getResult());

    // DB保存の処理
    User playerUser = UserMapper.selectUserByName(prin.getName());
    User opponentUser = UserMapper.selectUserById(id);
    Match match = new Match();
    match.setUser1(playerUser.getId());
    match.setUser2(opponentUser.getId());
    match.setUser1Hand(hand);
    match.setUser2Hand("グー");
    MatchMapper.insertMatch(match);
    model.addAttribute("user", opponentUser);

    return "match.html";
  }

  // CPUとの対戦(手は固定)
  @GetMapping("game")
  public String vsCpu(@RequestParam String hand, ModelMap model) {
    Janken cpu = new Janken();
    Janken player = new Janken(hand);
    player.judge(cpu.getHand());
    model.addAttribute("playerHand", player.getHand());
    model.addAttribute("cpuHand", cpu.getHand());
    model.addAttribute("result", player.getResult());
    return "janken.html";
  }
}
