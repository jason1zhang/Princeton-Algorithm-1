# Week 4 - 8 Puzzle

## Overview

Implementation of the N-puzzle solver using the A* search algorithm from Princeton University's "Algorithms, Part I" course.

## Problem Statement

Given a board with numbered tiles and one empty space, find the shortest sequence of moves to reach the goal state:
```
1  2  3
4  5  6
7  8  0
```

## Core Classes

### Board.java
Immutable data type representing a puzzle board.

- `Board(int[][] blocks)` - Construct board from 2D array
- `dimension()` - Board dimension n
- `hamming()` - Number of blocks out of place (cached)
- `manhattan()` - Sum of Manhattan distances to goal (cached)
- `isGoal()` - Is this the goal board?
- `neighbors()` - Iterable of adjacent boards (4-directional)
- `twin()` - Board with two tiles swapped (for solvability detection)
- `equals(Object)` - Board equality comparison

### Solver.java
A* search algorithm implementation.

- `Solver(Board initial)` - Find shortest solution using A*
- `isSolvable()` - Is the board solvable?
- `moves()` - Minimum number of moves (-1 if unsolvable)
- `solution()` - Sequence of boards in solution

## Algorithm

### A* Search
Uses priority queue with priority = `moves so far + Manhattan distance`

1. Insert initial board into MinPQ
2. Repeatedly extract minimum priority node
3. If goal found, reconstruct path
4. Insert unvisited neighbors

### Solvability Detection
Runs two simultaneous A* searches:
- One on the original board
- One on the "twin" board (two tiles swapped)

Exactly one is solvable. If original finishes first → solvable; if twin finishes first → unsolvable.

## Project Structure

```
week 4/
├── Board.java                      # Board data type
├── Solver.java                     # A* solver
├── Solver_100_full_testing.java    # Testing utility
├── 8puzzle-testing/              # Test puzzles
│   ├── puzzle*.txt               # 3x3 puzzles (8-puzzle)
│   ├── puzzle4x4-*.txt         # 4x4 puzzles (15-puzzle)
│   ├── puzzle2x2-*.txt          # 2x2 puzzles
│   ├── puzzle3x3-*.txt          # 3x3 puzzles
│   ├── puzzle3x3-*.txt          # 3x3 puzzles
│   ├── myPuzzle*.txt            # User-defined puzzles
│   └── *-unsolvable.txt        # Unsolvable variants
└── *.pdf                        # Assignment specifications
```

## Compile & Run

```bash
javac -cp ".:algs4.jar" Solver.java
java -cp ".:algs4.jar" Solver week\ 4/8puzzle-testing/puzzle04.txt
```

## Output Example

```
Minimum number of moves = 4
3  1  2
6  4  8
7  0  5

3  1  2
6  4  8
0  7  5

3  1  2
0  4  8
6  7  5

3  1  2
4  0  8
6  7  5

3  1  2
4  8  0
6  7  5
```

## Dependencies

- Java 8+
- algs4.jar
