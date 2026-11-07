package oit.is.z3634.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3634.kaizi.janken.model.Match;
import oit.is.z3634.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);
  public boolean dbUpdated = false;

  @Autowired
  private MatchMapper matchMapper;

  public ArrayList<Match> syncShowMatchesList() {
    return matchMapper.selectActiveMatches();
  }

  @Async
  public void asyncShowKekka(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          if (!this.syncShowMatchesList().isEmpty()) {
            dbUpdated = true;
          }
          continue;
        }
        // DBが更新されていれば更新後の試合結果を取得してsendし，1s休み，dbUpdatedをfalseにする
        ArrayList<Match> matches = this.syncShowMatchesList();
        if (matches.size() > 0) {
          Match match = matches.get(0);
          emitter.send(match);
          TimeUnit.MILLISECONDS.sleep(1000);
          dbUpdated = false;
          match.setIsActive(false);
          matchMapper.updateMatch(match);
        } else {
          dbUpdated = false;
        }
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
  }

}
