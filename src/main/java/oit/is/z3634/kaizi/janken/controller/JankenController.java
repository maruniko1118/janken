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
import oit.is.z3634.kaizi.janken.model.MatchInfo;
import oit.is.z3634.kaizi.janken.model.MatchInfoMapper;
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

  @Autowired
  private MatchInfoMapper MatchInfoMapper;

  // GETで "/janken" にアクセスがあった場合
  // ユーザー名を取得せず、janken.htmlを表示
  @GetMapping()
  public String janken(Principal prin, ModelMap model) {
    ArrayList<User> users = UserMapper.selectAllUser();
    ArrayList<Match> matches = MatchMapper.selectAllMatch();
    ArrayList<MatchInfo> matchInfos = MatchInfoMapper.selectActiveMatchInfo();
    String username = prin.getName();
    this.entry.addUser(username);
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    model.addAttribute("matchInfos", matchInfos);
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
    User player = UserMapper.selectUserByName(prin.getName());
    User opponent = UserMapper.selectUserById(id);

    ArrayList<MatchInfo> matchInfos = MatchInfoMapper
        .selectActiveMatchInfoByUsername(player.getId());

    Janken janken = new Janken();

    if (matchInfos.size() > 0) {
      MatchInfo activeMatchInfo = matchInfos.get(0);
      String result = janken.judge(activeMatchInfo.getUser1Hand(), hand);
      model.addAttribute("playerHand", hand);
      model.addAttribute("opponentHand", activeMatchInfo.getUser1Hand());
      model.addAttribute("result", result);
      model.addAttribute("user", opponent);

      // DB保存の処理
      Match match = new Match();
      match.setUser1(activeMatchInfo.getUser1());
      match.setUser2(player.getId());
      match.setUser1Hand(activeMatchInfo.getUser1Hand());
      match.setUser2Hand(hand);
      match.setIsActive(true);
      MatchMapper.insertMatch(match);

      // matchinfo の該当レコードの isActive を FALSE に更新
      activeMatchInfo.setIsActive(false);
      MatchInfoMapper.updateMatchInfo(activeMatchInfo);

      return "match.html";

    } else {

      MatchInfo matchInfo = new MatchInfo();
      matchInfo.setUser1(player.getId());
      matchInfo.setUser2(opponent.getId());
      matchInfo.setUser1Hand(hand);
      matchInfo.setIsActive(true);
      MatchInfoMapper.insertMatchInfo(matchInfo);
      return "wait.html";

    }
  }
}
