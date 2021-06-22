package me.bcoffield.magicsquare.lucas;

import me.bcoffield.magicsquare.domain.MagicSquare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LucasRandomC {
  /**
   * This looks like a 16yo coded this method. And I apologize. Refactor later...
   */
  public void run() {
    boolean found = false;
    while (!found) {
      int c = Math.max(1, Math.abs(new Random().nextInt()));
      long cSquared = (long) c * c;

      LucasCandidates candidates = getCandidates(c, cSquared);

      if (candidates.size() > 7) {
        for (int i = 0; i < candidates.getLows().size(); i++) {
          long a = cSquared - candidates.getLows().get(i);
          for (int j = 0; j < candidates.getLows().size(); j++) {
            if (j <= i) continue;
            long b = cSquared - candidates.getLows().get(j);
            if (cSquared - a <= b) continue;
            if (b == 2 * a) {
              System.err.println("b != 2a rule broken!");
              continue;
            }
            if (candidates.getLows().contains(cSquared + (a - b))
                && candidates.getLows().contains(cSquared - (a + b))) {
              System.out.println("Found one! c: " + c + " cSquared: " + cSquared);
              System.out.println(
                  "Lows: "
                      + Arrays.toString(candidates.getLows().stream().mapToLong(l -> l).toArray()));
              System.out.println(
                  "Highs: "
                      + Arrays.toString(
                          candidates.getHighs().stream().mapToLong(l -> l).toArray()));
              System.out.println("a: " + a);
              System.out.println("b: " + b);
              List<Long> longs = new ArrayList<>();
              longs.add(cSquared);
              longs.add(candidates.getLows().get(i));
              longs.add(candidates.getLows().get(j));
              longs.add(candidates.getLows().get(candidates.getLows().indexOf(cSquared + (a - b))));
              longs.add(candidates.getLows().get(candidates.getLows().indexOf(cSquared - (a + b))));
              longs.add(candidates.getHighs().get(i));
              longs.add(candidates.getHighs().get(j));
              longs.add(
                  candidates.getHighs().get(candidates.getLows().indexOf(cSquared + (a - b))));
              longs.add(
                  candidates.getHighs().get(candidates.getLows().indexOf(cSquared - (a + b))));
              MagicSquare magicSquare = new MagicSquare(longs.stream().mapToLong(l -> l).toArray());
              if (magicSquare.isValid()) {
                found = true;
                System.out.println(magicSquare);
              } else {
                System.err.println("Something went wrong");
                System.err.println(magicSquare);
              }
            }
          }
        }
      }
      if (!found) {
        System.out.println("Failure for c=" + c);
      }
    }
  }

  protected LucasCandidates getCandidates(int c, long cSquared) {
    int smallerI = c - 1;
    int largerI = c + 1;

    LucasCandidates candidates = new LucasCandidates();
    while (smallerI >= 1 && largerI < Integer.MAX_VALUE) {
      long smallerISquared = (long) smallerI * smallerI;
      long largerISquared = (long) largerI * largerI;
      long smallerSquaredDiff = cSquared - smallerISquared;
      long largerSquaredDiff = largerISquared - cSquared;
      if (smallerSquaredDiff < largerSquaredDiff) {
        smallerI--;
      } else if (largerSquaredDiff < smallerSquaredDiff) {
        largerI++;
      } else {
        candidates.getLows().add(smallerISquared);
        candidates.getHighs().add(largerISquared);
        smallerI--;
        largerI++;
      }
    }
    return candidates;
  }
}
