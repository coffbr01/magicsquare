package me.bcoffield.magicsquare.lucas;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LucasCandidates {
  private final List<Long> lows = new ArrayList<>();
  private final List<Long> highs = new ArrayList<>();

  public int size() {
    return lows.size() + highs.size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }
}
