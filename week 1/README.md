# Princeton Algorithm 1 - Percolation

## Overview

Implementation of the Percolation problem from Princeton University's "Algorithms, Part I" course on Coursera.

## What is Percolation?

Percolation theory models connectivity in random systems. A grid of sites is either blocked or open, and water flows from the top through open sites. The system percolates if water can reach the bottom row.

## Project Structure

```
.
├── algs4.jar                          # Algorithms 4th Edition library
└── week 1/
    ├── Percolation.java               # Core data structure
    ├── PercolationStats.java          # Monte Carlo simulation
    ├── PercolationVisualizer.java     # Visualization from input file
    ├── InteractivePercolationVisualizer.java  # Interactive visualization
    ├── percolation-testing/           # Test data
    └── *.pdf                          # Assignment specifications
```

## Dependencies

- Java 8+
- [algs4.jar](https://github.com/kevin-wayne/algs4) - Algorithms, 4th Edition library

## Compile & Run

```bash
# Compile
javac -cp ".:algs4.jar" week\ 1/Percolation.java

# Run
java -cp ".:algs4.jar" Percolation
```

## Algorithm

- **Data Structure**: Weighted Quick Union Find with path compression
- **Time Complexity**: Nearly constant amortized time for union/find operations
- **Space Complexity**: O(n²) for the grid

## Test Data

The `percolation-testing/` folder contains various test inputs including ASCII art patterns for validation.
