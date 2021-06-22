package me.bcoffield.magicsquare.domain;

import lombok.Getter;

import java.util.stream.LongStream;

@Getter
public class MagicSquare {
  private final long[] longs;

  public MagicSquare(long... longs) {
    this.longs = longs;
  }

  public boolean isValid() {
    long goal = sumTopRow();
    if (sumMidRow() != goal) {
      return false;
    }
    if (sumBotRow() != goal) {
      return false;
    }
    if (sumLeftCol() != goal) {
      return false;
    }
    if (sumMidCol() != goal) {
      return false;
    }
    if (sumRightCol() != goal) {
      return false;
    }
    if (sumUpDiag() != goal) {
      return false;
    }
    if (sumDownDiag() != goal) {
      return false;
    }

    return isUnique();
  }

  protected boolean isUnique() {
    return LongStream.of(longs).distinct().toArray().length == longs.length;
  }

  private long sumTopRow() {
    return longs[0] + longs[1] + longs[2];
  }

  private long sumMidRow() {
    return longs[3] + longs[4] + longs[5];
  }

  private long sumBotRow() {
    return longs[6] + longs[7] + longs[8];
  }

  private long sumLeftCol() {
    return longs[0] + longs[3] + longs[6];
  }

  private long sumMidCol() {
    return longs[1] + longs[4] + longs[7];
  }

  private long sumRightCol() {
    return longs[2] + longs[5] + longs[8];
  }

  private long sumUpDiag() {
    return longs[6] + longs[4] + longs[2];
  }

  private long sumDownDiag() {
    return longs[0] + longs[4] + longs[8];
  }

  @Override
  public String toString() {
    return "\n"
        + "unique: "
        + isUnique()
        + "\n"
        + "valid: "
        + isValid()
        + "\n|"
        + longs[0]
        + "|"
        + longs[1]
        + "|"
        + longs[2]
        + "|\n|"
        + longs[3]
        + "|"
        + longs[4]
        + "|"
        + longs[5]
        + "|\n|"
        + longs[6]
        + "|"
        + longs[7]
        + "|"
        + longs[8]
        + "|";
  }
}
