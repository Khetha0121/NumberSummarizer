# NumberSummarizerAssessment

## NumberRangeSummarizerImpl.java

### Assumptions
- Input may contain whitespace between elements (e.g. `" 1 , 2 , 3 "` is valid).
- `collect("")` or `collect("   ")` returns an empty unmodifiable list.
- Invalid numeric elements (e.g. `"1,abc,2"`) throw an `IllegalArgumentException` (wrapping `NumberFormatException`).
- Duplicates are removed automatically (due to use of `TreeSet`).
- Input is automatically sorted (also via `TreeSet`).
- Consecutive integers are summarized into ranges using `"start-end"`.
- Zeros and negative numbers are supported.
- `summarizeCollection(null)` or an empty collection returns an empty string.

### Features
- Parses, trims, deduplicates, and sorts input using a `TreeSet`.
- Summarizes sequences of consecutive integers into compact ranges.
- Uses helper method `formatRange(start, end)` to cleanly format ranges and single numbers.
- Produces output as a comma-delimited string with ranges collapsed.
- Includes a CLI (`main`) that prompts the user for input, parses it, and prints results.

---

## NumberRangeSummarizerImplTest.java

### Assumptions
- Written with **JUnit 5** (`org.junit.jupiter.api`).
- Tests expect invalid input (e.g. `"1,abc,2"`) to throw an `IllegalArgumentException`.
- Tests expect whitespace handling (e.g. `" 1 , 2 , 3 "`) to be valid.
- Tests expect duplicates to be removed and numbers sorted.
- Tests cover edge cases such as negative numbers, large numbers, integer boundaries, and nulls.
- Tests allow `summarize(null)` and arrays containing `null` to throw `NullPointerException`.

### Features
- Verifies example input provided (`"1,3,6,7,8,12,13,14,15,21,22,23,24,31" → "1, 3, 6-8, 12-15, 21-24, 31"`).
- Covers empty and null entries.
- Verifies deduplication and sorting.
- Validates that invalid inputs throw exceptions.
- Checks formatting of consecutive and non-consecutive numbers.
- Tests negatives, zero, and large integer sequences.
- Tests both direct array input to `summarize()` and collection-based input to `summarizeCollection()`.

---

## Example

**Input:**
