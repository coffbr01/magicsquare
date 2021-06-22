package me.bcoffield.magicsquare.lucas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LucasRandomCTest {
  private static final LucasRandomC tested = new LucasRandomC();

  @Test
  public void testGetCandidates() {
    int c = 1107527113;
    long cSquared = (long) c * c;
    LucasCandidates candidates = tested.getCandidates(c, cSquared);
    assertEquals(8, candidates.size());
    assertEquals(cSquared - candidates.getLows().get(0), candidates.getHighs().get(0) - cSquared);
    assertEquals(cSquared - candidates.getLows().get(1), candidates.getHighs().get(1) - cSquared);
    assertEquals(cSquared - candidates.getLows().get(2), candidates.getHighs().get(2) - cSquared);
    assertEquals(cSquared - candidates.getLows().get(3), candidates.getHighs().get(3) - cSquared);
  }

  @Test
  public void testGetCandidates_noCandidatesLow() {
    int c = 4;
    long cSquared = (long) c * c;
    LucasCandidates candidates = tested.getCandidates(c, cSquared);
    assertTrue(candidates.isEmpty());
  }

  @Test
  public void testGetCandidates_noCandidatesHigh() {
    int c = Integer.MAX_VALUE - 4;
    long cSquared = (long) c * c;
    LucasCandidates candidates = tested.getCandidates(c, cSquared);
    assertTrue(candidates.isEmpty());
  }
}
