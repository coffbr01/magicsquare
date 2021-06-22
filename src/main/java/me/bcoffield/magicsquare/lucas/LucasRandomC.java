package me.bcoffield.magicsquare.lucas;

import me.bcoffield.magicsquare.domain.MagicSquare;

import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LucasRandomC {
  private Connection conn;
  private static final AtomicInteger atomicC = new AtomicInteger(1);
  private static long lastLogTime = System.currentTimeMillis();

  /** This looks like a 16yo coded this method. And I apologize. Refactor later... */
  public void run() {
    createConnection();
    setInitialC();

    boolean found = false;
    while (!found) {
      int c = atomicC.getAndAdd(1);
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
                markCSuccess(c, true);
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
        if (c % 10000 == 0) {
          System.out.println("No solution found through c=" + NumberFormat.getInstance().format(c) + " (took " + (System.currentTimeMillis() - lastLogTime) + "ms)");
          lastLogTime = System.currentTimeMillis();
        }
        markCSuccess(c, false);
      }
    }
  }

  private void setInitialC() {
    Statement stmt;
    try {
      stmt = conn.createStatement();
      String sql = "SELECT MAX(ID) FROM C;";
      ResultSet resultSet = stmt.executeQuery(sql);
      if (resultSet.next()) {
        atomicC.set(resultSet.getInt(1) + 1);
      }
      stmt.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void markCSuccess(int c, boolean success) {
    reconnectIfNecessary();
    Statement stmt;
    try {
      stmt = conn.createStatement();
      String sql =
          "INSERT INTO C (ID, SUCCESS) VALUES (" + c + ", " + (success ? "TRUE" : "FALSE") + ");";
      stmt.executeUpdate(sql);
      stmt.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void reconnectIfNecessary() {
    try {
      if (conn.isClosed()) {
        createConnection();
      }
    } catch (SQLException e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void createConnection() {
    try {
      Class.forName("org.postgresql.Driver");
      conn =
          DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/magicsquare", "magicsquare", "password");
      System.out.println("Opened database successfully");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
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
