# Week 5 - KD-Trees

## Overview

Implementation of 2D spatial search data structures from Princeton University's "Algorithms, Part I" course.

## Problem Statement

Given a set of points in the unit square (coordinates between 0 and 1), support:
- **Range search**: Find all points contained in a query rectangle
- **Nearest neighbor search**: Find the closest point to a query point

## Core Classes

### PointSET.java
Brute-force implementation using `SET<Point2D>`.

- `insert(Point2D p)` - Add point to set
- `contains(Point2D p)` - Check if point exists
- `range(RectHV rect)` - Find all points in rectangle (brute force)
- `nearest(Point2D p)` - Find closest point (brute force)

### KdTree.java
Efficient KD-tree implementation with recursive space partitioning.

- `insert(Point2D p)` - Insert point into tree
- `contains(Point2D p)` - Check if point exists
- `range(RectHV rect)` - Find all points in rectangle (pruned search)
- `nearest(Point2D p)` - Find closest point (pruned search)

**How it works:**
- Alternates splitting by x and y coordinates at each level
- Red line segments = vertical splits (x-coordinate)
- Green line segments = horizontal splits (y-coordinate)
- Uses axis-aligned rectangles to prune search space

## Algorithm Details

### KD-Tree Insertion
```
At root: split by x-coordinate
  Left subtree: points with x < root.x
  Right subtree: points with x >= root.x
At next level: split by y-coordinate
  Bottom subtree: points with y < root.y
  Top subtree: points with y >= root.y
Continue alternating...
```

### Nearest Neighbor Search
```
1. Start at root
2. Recursively search the "suspicious" side first
3. Prune entire subtree if its rectangle is farther than current best
4. Update best if closer point found
```

## Complexity Comparison

| Operation | PointSET (Brute) | KdTree |
|-----------|-----------------|--------|
| insert | O(n) | O(log n) |
| contains | O(n) | O(log n) |
| range | O(n) | O(log n + k) |
| nearest | O(n) | O(log n) |

*k = number of points in range*

## Project Structure

```
week 5/
├── PointSET.java                      # Brute-force implementation
├── KdTree.java                        # KD-tree implementation
├── KdTreeVisualizer.java             # Visualizes KD-tree construction
├── NearestNeighborVisualizer.java     # Interactive nearest neighbor search
├── RangeSearchVisualizer.java        # Range search visualization
├── KdTreeGenerator.java               # Test data generator
├── kdtree-testing/                    # Test inputs
│   ├── input*.txt                   # Random points (10 to 1M)
│   ├── circle*.txt                  # Points on circles
│   ├── horizontal*.txt / vertical*.txt
│   └── *.png                       # Reference images
└── *.pdf                           # Assignment specifications
```

## Compile & Run

```bash
# Compile
javac -cp ".:algs4.jar" PointSET.java
javac -cp ".:algs4.jar" KdTree.java

# Run nearest neighbor visualizer
java -cp ".:algs4.jar" NearestNeighborVisualizer week\ 5/kdtree-testing/input10.txt

# Run range search visualizer
java -cp ".:algs4.jar" RangeSearchVisualizer week\ 5/kdtree-testing/input10.txt

# Run KD-tree visualizer
java -cp ".:algs4.jar" KdTreeVisualizer week\ 5/kdtree-testing/input10.txt
```

## Dependencies

- Java 8+
- algs4.jar
