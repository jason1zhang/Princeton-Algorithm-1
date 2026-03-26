# Week 3 - Collinear Points

## Overview

Implementation of algorithms to detect collinear points from Princeton University's "Algorithms, Part I" course.

## Problem Statement

Given n points in the plane, find all line segments containing 4 or more points.

## Core Classes

### Point.java
Immutable data type for points in the plane.

- `slopeTo(Point that)` - Returns slope between this point and that point
- `compareTo(Point that)` - Compares by y-coordinate, then x-coordinate
- `slopeOrder()` - Returns comparator sorting points by slope

### LineSegment.java
Represents a line segment between two points with `toString()` and `draw()` methods.

### BruteCollinearPoints.java
Brute-force solution examining every 4-point combination.

- Time Complexity: O(n⁴)
- Checks all combinations of 4 points for equal slopes

### FastCollinearPoints.java
Optimized sort-based solution.

- Time Complexity: O(n² log n)
- For each point, sorts remaining points by slope
- Detects sequences of 3+ points with same slope (4+ total including anchor)

## Project Structure

```
week 3/
├── Point.java                      # Point data type
├── LineSegment.java                # Line segment representation
├── BruteCollinearPoints.java       # Brute-force solution
├── FastCollinearPoints.java        # Fast sort-based solution
├── Brute.java                      # Client for brute-force visualization
├── Fast.java                       # Client for fast visualization
├── collinear-testing/              # Test data
│   ├── input*.txt                  # Point coordinates
│   ├── grid*.txt                   # Grid patterns
│   ├── horizontal*.txt            # Horizontal line tests
│   ├── vertical*.txt              # Vertical line tests
│   ├── random*.txt                # Random point sets
│   └── *.png                      # Reference visualizations
└── *.pdf                          # Assignment specifications
```

## Algorithm Details

### Brute Force
```
For each point p:
  For each point q:
    For each point r:
      For each point s:
        If slope(p,q) == slope(p,r) == slope(p,s):
          Found collinear points
```

### Fast Approach
```
For each point p:
  Sort remaining points by slope to p
  Scan sorted array for sequences with same slope
  If sequence length >= 3, found collinear segment
```

## Compile & Run

```bash
javac -cp ".:algs4.jar" Brute.java
java -cp ".:algs4.jar" Brute week\ 3/collinear-testing/input100.txt

javac -cp ".:algs4.jar" Fast.java
java -cp ".:algs4.jar" Fast week\ 3/collinear-testing/input100.txt
```

## Dependencies

- Java 8+
- algs4.jar
