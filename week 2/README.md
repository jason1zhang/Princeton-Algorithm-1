# Week 2 - Queues

## Overview

Implementation of generic queue data structures from Princeton University's "Algorithms, Part I" course.

## Data Structures

### Deque.java
A **double-ended queue** (deque) implemented as a doubly linked list.

- `addFirst(Item)` - Add to front - O(1)
- `addLast(Item)` - Add to back - O(1)
- `removeFirst()` - Remove from front - O(1)
- `removeLast()` - Remove from back - O(1)
- `iterator()` - Iterate front to back

### RandomizedQueue.java
A **randomized queue** implemented with a resizing array.

- `enqueue(Item)` - Add item - O(1) amortized
- `dequeue()` - Remove and return random item - O(1)
- `sample()` - Return random item without removal - O(1)
- `iterator()` - Iterate in random order

Key optimization: Swap random element with last element on dequeue for O(1) deletion.

### Permutation.java
Client program that reads strings and prints k random items.

```bash
# Example: Print 3 random items from input
echo "A B C D E" | java Permutation 3
```

## Project Structure

```
week 2/
├── Deque.java                      # Doubly linked list implementation
├── RandomizedQueue.java            # Resizing array with random removal
├── Permutation.java                # Random k-item selection client
├── queues/                         # Test data
│   ├── tinyTale.txt
│   ├── mediumTale.txt
│   ├── tale.txt
│   ├── duplicates.txt
│   └── permutation*.txt
└── *.pdf                           # Assignment specifications
```

## Dependencies

- Java 8+
- algs4.jar

## Compile & Run

```bash
javac -cp ".:algs4.jar" Deque.java
java -cp ".:algs4.jar" Deque < input.txt
```
