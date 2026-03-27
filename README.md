# Princeton Algorithm 1

Implementation of assignments from Princeton University's **"Algorithms, Part I"** course on Coursera.

## Course Overview

This repository contains solutions to the programming assignments covering fundamental algorithms and data structures.

## Assignments

### Week 1 - Percolation
Statistical physics simulation using **Weighted Quick Union Find** to detect connectivity in a grid.

- `Percolation.java` - Grid data structure with union-find
- `PercolationStats.java` - Monte Carlo simulation

### Week 2 - Queues
Generic queue implementations using linked lists and resizing arrays.

- `Deque.java` - Double-ended queue (doubly linked list)
- `RandomizedQueue.java` - Random selection queue (resizing array)
- `Permutation.java` - Random k-item selection client

### Week 3 - Collinear Points
Finding line segments containing 4+ collinear points.

- `Point.java` - 2D point data type
- `BruteCollinearPoints.java` - O(n⁴) brute-force solution
- `FastCollinearPoints.java` - O(n² log n) sort-based solution

### Week 4 - 8 Puzzle
N-puzzle solver using **A* search algorithm**.

- `Board.java` - Puzzle board representation
- `Solver.java` - A* search with Manhattan distance heuristic
- `SolverVisualizer.java` - Animated visualization

### Week 5 - KD-Trees
2D spatial search data structures.

- `PointSET.java` - Brute-force implementation
- `KdTree.java` - KD-tree with recursive space partitioning
- Visualizers for nearest neighbor and range search

## Repository Structure

```
.
├── algs4.jar                    # Algorithms 4th Edition library
├── week 1/                      # Percolation
├── week 2/                      # Queues
├── week 3/                      # Collinear Points
├── week 4/                      # 8 Puzzle
└── week 5/                      # KD-Trees
```

## Dependencies

- Java 8+
- [algs4.jar](https://github.com/kevin-wayne/algs4) - Algorithms, 4th Edition library

## Compile & Run

```bash
# Set classpath
export CLASSPATH=".:algs4.jar"

# Compile
javac week\ 1/Percolation.java

# Run
java week\ 1/Percolation
```

## Acknowledgments

- **Princeton University**: [Algorithms, Part I](https://www.coursera.org/learn/algorithms-part1)
- **Robert Sedgewick & Kevin Wayne**: [Algorithms, 4th Edition](https://algs4.cs.princeton.edu/home/)
