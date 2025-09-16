# NumberSummarizerAssessment

## Executive Summary
The Number Summarizer is a simple Java program that takes a comma-separated string of integers, parses them into a sorted collection without duplicates, and produces a compact summary where consecutive sequences are collapsed into ranges (`start-end`).  

## How It Works
1. Input string (e.g. `"1,3,6,7,8,12,13,14,15,21,22,23,24,31"`) is parsed into integers.
2. Whitespace is stripped, duplicates are removed, and numbers are automatically sorted.
3. Consecutive numbers are grouped into ranges.
4. The final summary string is returned, e.g.:
1, 3, 6-8, 12-15, 21-24, 31

## Assumptions
- Input may contain whitespace between numbers (valid).
- Empty or blank input returns an empty unmodifiable list.
- Invalid numeric values throw an `IllegalArgumentException`.
- Duplicates are automatically removed.
- Input is sorted in ascending order.
- Supports negative numbers and zero.
- Consecutive numbers are collapsed into `"start-end"` format.
- Null or empty collections summarize to an empty string.

---
## Project Structure

### 1. `NumberRangeSummarizer` (Interface)
Defines the contract:
- `collect(String input)`: Parse a string of numbers into a collection.
- `summarizeCollection(Collection<Integer> input)`: Produce a summarized string of ranges.

This ensures flexibility — different implementations can be provided without changing the contract.

---

### 2. `NumberRangeSummarizerImpl` (Implementation)
- **Parsing (`collect`)**
- Splits input on commas.
- Strips whitespace.
- Uses `TreeSet` to sort numbers and remove duplicates.
- Throws `IllegalArgumentException` for invalid numbers.

- **Summarization (`summarizeCollection`)**
- Converts the collection to an array for sequential iteration.
- Detects breakpoints where numbers stop being consecutive.
- Formats single numbers or ranges using `formatRange(start, end)`.

- **Helper (`formatRange`)**
- Returns a single number (`"5"`) or range (`"1-5"`).

- **Main Method**
- Provides a command-line interface.
- Prompts the user for input, parses, and prints results.

---

### 3. `NumberRangeSummarizerImplTest` (JUnit 5 Tests)
Thoroughly tests parsing and summarization logic.

- **Collect() tests**
- Handles `null`, empty, and blank input.
- Validates whitespace handling.
- Verifies duplicates are removed and numbers sorted.
- Ensures invalid input throws `IllegalArgumentException`.

- **SummarizeCollection() tests**
- Empty or null collections return `""`.
- Verifies collapsing of consecutive sequences.
- Handles negative numbers and large numbers.
- Validates edge cases like single elements and two-element collections.

- **End-to-End tests**
- Verifies example from assignment (`"1,3,6,7,8,12,13,14,15,21,22,23,24,31"` → `"1, 3, 6-8, 12-15, 21-24, 31"`).
- Checks behavior for all consecutive numbers, all non-consecutive numbers, unsorted input, and duplicates.

---

## Example Run

**Input:**
1,3,6,7,8,12,13,14,15,21,22,23,24,31

**Output:**
Original input: 1,3,6,7,8,12,13,14,15,21,22,23,24,31
Parsed numbers: [1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31]
Range summary: 1, 3, 6-8, 12-15, 21-24, 31

---

## Requirements to Run

### Java Development Kit (JDK) 8 or higher
- Needed to compile and run the Java files.  
- Set `JAVA_HOME` and ensure `java` & `javac` are in your system PATH.  

### IDE or Text Editor (optional)
- Eclipse (used in development), IntelliJ IDEA, or VS Code.  
- Optional if using the command line.  

### JUnit 5 Library (for running tests)
- Required only if you want to run `NumberRangeSummarizerImplTest.java`.  
- In Eclipse:  
  1. Right-click project → **Build Path → Add Library → JUnit**  
  2. Select **JUnit 5**  
