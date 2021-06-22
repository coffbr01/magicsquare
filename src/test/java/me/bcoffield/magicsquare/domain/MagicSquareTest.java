package me.bcoffield.magicsquare.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicSquareTest {
    @Test
    public void testValid() {
        assertTrue(new MagicSquare(8, 1, 6, 3, 5, 7, 4, 9, 2).isValid());
    }

    @Test
    public void testInvalid() {
        assertFalse(new MagicSquare(1, 2, 3, 4, 5, 6, 7, 8, 9).isValid());
    }

    @Test
    public void testUnique() {
        assertTrue(new MagicSquare(1, 2, 3, 4, 5, 6, 7, 8, 9).isUnique());
    }

    @Test
    public void testNotUnique() {
        assertFalse(new MagicSquare(1, 1, 3, 4, 5, 6, 7, 8, 9).isUnique());
    }
}