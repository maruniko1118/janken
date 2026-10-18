package oit.is.z3634.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3634.kaizi.janken.model.Janken;

// HTTPリクエストがあったときに呼び出されるクラス
@Controller
public class JankenController {

  // GETで "/janken" にアクセスがあった場合
  // ユーザー名を取得せず、janken.htmlを表示
  @GetMapping("/janken")
  public String janken() {
    return "janken.html";
  }

  // POSTで "/janken" にアクセスがあった場合
  // ユーザー名を取得し、janken.htmlを表示
  @PostMapping("/janken")
  public String janken(@RequestParam String username, ModelMap model) {
    model.addAttribute("username", username);
    return "janken.html";
  }

  // CPUとの対戦(手は固定)
  @GetMapping("/jankengame")
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
