# magicsquare

Attempts to find a 3x3 magic square of squares. Like
[Parker's Square](https://en.wikipedia.org/wiki/Magic_square#Parker_square) but hopefully better.

## Setup

* [Install postgres](https://www.postgresql.org/download/)
* create a `magicsquare` user
  ```
  CREATE USER magicsquare WITH ENCRYPTED PASSWORD 'password';
  ```
* create a `magicsquare` database
  ```
  CREATE DATABASE magicsquare OWNER magicsquare;
  ```
* create a `c` table
  ```
  CREATE TABLE c (ID INT PRIMARY KEY NOT NULL, SUCCESS BOOLEAN NOT NULL DEFAULT FALSE);
  ```

## Execution

Run Main.java to attempt to find a solution.

## Lucas Algorithm

* Given a positive integer value of `c`
* Calculate `c^2`
* Calculate all pairs of equidistant squares from `c^2`, such that `pairedInt1 < c^2 < pairedInt2` and
  `c^2 - pairedInt1 = pairedInt2 - c^2`
* Select 4 pairs (8 other ints) to enter into a magic square, along with `c^2`. See if any permutation of those numbers
satisfies the magic square of squares.
* Select a different set of 4 pairs from the list of pairs and try the previous step again; continue until all pair
combinations have been tried
* If none work, mark `c` as a failed attempt and move on to a different value of `c`
