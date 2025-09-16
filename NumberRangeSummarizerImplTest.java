package numberrangesummarizer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

class NumberRangeSummarizerImplTest {
    
    private NumberRangeSummarizerImpl summarizer = new NumberRangeSummarizerImpl();

    // Test collect() method
    @Test
    void testCollect_NullInput() {
        Collection<Integer> result = summarizer.collect(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCollect_EmptyString() {
        Collection<Integer> result = summarizer.collect("");
        assertTrue(result.isEmpty());
    }

    @Test
    void testCollect_BlankString() {
        Collection<Integer> result = summarizer.collect("   ");
        assertTrue(result.isEmpty());
    }

    @Test
    void testCollect_SingleNumber() {
        Collection<Integer> result = summarizer.collect("5");
        assertEquals(1, result.size());
        assertTrue(result.contains(5));
    }

    @Test
    void testCollect_MultipleNumbers() {
        Collection<Integer> result = summarizer.collect("1,2,3,4,5");
        assertEquals(5, result.size());
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    void testCollect_NumbersWithSpaces() {
        Collection<Integer> result = summarizer.collect(" 1 , 2 , 3 ");
        assertEquals(3, result.size());
        assertArrayEquals(new Integer[]{1, 2, 3}, result.toArray());
    }

    @Test
    void testCollect_DuplicateNumbers() {
        Collection<Integer> result = summarizer.collect("1,2,2,3,3,3");
        assertEquals(3, result.size()); // Duplicates should be removed
        assertArrayEquals(new Integer[]{1, 2, 3}, result.toArray());
    }

    @Test
    void testCollect_UnsortedNumbers() {
        Collection<Integer> result = summarizer.collect("5,1,3,2,4");
        // Should be sorted automatically by TreeSet
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, result.toArray());
    }

    @Test
    void testCollect_InvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            summarizer.collect("1,2,abc,4");
        });
    }

    // Test summarizeCollection() method
    @Test
    void testSummarizeCollection_NullInput() {
        String result = summarizer.summarizeCollection(null);
        assertEquals("", result);
    }

    @Test
    void testSummarizeCollection_EmptyCollection() {
    	
        String result = summarizer.summarizeCollection(Collections.emptyList());
        assertEquals("", result);
    }

    @Test
    void testSummarizeCollection_SingleNumber() {
        Collection<Integer> input = summarizer.collect("5");
        String result = summarizer.summarizeCollection(input);
        assertEquals("5", result);
    }

    @Test
    void testSummarizeCollection_ConsecutiveNumbers() {
        Collection<Integer> input = summarizer.collect("1,2,3,4,5");
        String result = summarizer.summarizeCollection(input);
        assertEquals("1-5", result);
    }

    @Test
    void testSummarizeCollection_NonConsecutiveNumbers() {
        Collection<Integer> input = summarizer.collect("1,3,5,7,9");
        String result = summarizer.summarizeCollection(input);
        assertEquals("1, 3, 5, 7, 9", result);
    }

    @Test
    void testSummarizeCollection_MixedSequence() {
        Collection<Integer> input = summarizer.collect("1,2,3,5,7,8,9,10,12");
        String result = summarizer.summarizeCollection(input);
        assertEquals("1-3, 5, 7-10, 12", result);
    }

    @Test
    void testSummarizeCollection_NegativeNumbers() {
        Collection<Integer> input = summarizer.collect("-3,-2,-1,0,1,2");
        String result = summarizer.summarizeCollection(input);
        assertEquals("-3-2", result);
    }

    @Test
    void testSummarizeCollection_LargeNumbers() {
        Collection<Integer> input = summarizer.collect("1000,1001,1002,1005,1006");
        String result = summarizer.summarizeCollection(input);
        assertEquals("1000-1002, 1005-1006", result);
    }

    // Test end-to-end functionality
    @Test
    void testEndToEnd_BasicExample() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        String expected = "1, 3, 6-8, 12-15, 21-24, 31";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }

    @Test
    void testEndToEnd_AllConsecutive() {
        String input = "1,2,3,4,5,6,7,8,9,10";
        String expected = "1-10";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }

    @Test
    void testEndToEnd_AllNonConsecutive() {
        String input = "1,5,10,15,20";
        String expected = "1, 5, 10, 15, 20";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }

    @Test
    void testEndToEnd_WithDuplicates() {
        String input = "1,2,2,3,4,4,5,6,6,7";
        String expected = "1-7";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }

    @Test
    void testEndToEnd_UnsortedInput() {
        String input = "10,5,3,8,1,2,7,6,4,9";
        String expected = "1-10";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }

    // Edge cases
    @Test
    void testEdgeCase_SingleElementRange() {
        String input = "5";
        String expected = "5";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }

    @Test
    void testEdgeCase_TwoConsecutiveNumbers() {
        String input = "5,6";
        String expected = "5-6";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }

    @Test
    void testEdgeCase_TwoNonConsecutiveNumbers() {
        String input = "5,7";
        String expected = "5, 7";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }

    @Test
    void testEdgeCase_LargeGap() {
        String input = "1,2,3,100,101,102";
        String expected = "1-3, 100-102";
        
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        assertEquals(expected, result);
    }
    
    @Test
    void testSingleElement() {
        Integer[] input = {5};
        String result = summarizer.summarize(input);
        assertEquals("5", result);
    }
    
    @Test
    void testTwoConsecutiveNumbers() {
        Integer[] input = {1, 2};
        String result = summarizer.summarize(input);
        assertEquals("1-2", result);
    }
    
    @Test
    void testTwoNonConsecutiveNumbers() {
        Integer[] input = {1, 3};
        String result = summarizer.summarize(input);
        assertEquals("1, 3", result);
    }
    
    @Test
    void testSimpleRange() {
        Integer[] input = {1, 2, 3, 4, 5};
        String result = summarizer.summarize(input);
        assertEquals("1-5", result);
    }
    
    @Test
    void testMixedRangesAndSingles() {
        Integer[] input = {1, 2, 4, 5, 7};
        String result = summarizer.summarize(input);
        assertEquals("1-2, 4-5, 7", result);
    }
    
    @Test
    void testComplexMixedRanges() {
        Integer[] input = {1, 2, 3, 5, 7, 8, 9, 11, 13, 14, 15};
        String result = summarizer.summarize(input);
        assertEquals("1-3, 5, 7-9, 11, 13-15", result);
    }
    
    @Test
    void testNegativeNumbers() {
        Integer[] input = {-3, -2, -1, 0, 1};
        String result = summarizer.summarize(input);
        assertEquals("-3-1", result);
    }
    
    @Test
    void testDuplicateNumbers() {
        Integer[] input = {1, 2, 2, 3, 4};
        String result = summarizer.summarize(input);
        assertEquals("1-4", result);
    }
    
    @Test
    void testLargeNumbers() {
        Integer[] input = {1000, 1001, 1003, 1004, 1005};
        String result = summarizer.summarize(input);
        assertEquals("1000-1001, 1003-1005", result);
    }
    
    @Test
    void testIntegerBoundaries() {
        Integer[] input = {Integer.MAX_VALUE - 1, Integer.MAX_VALUE};
        String result = summarizer.summarize(input);
        String expected = (Integer.MAX_VALUE - 1) + "-" + Integer.MAX_VALUE;
        assertEquals(expected, result);
    }
    
    @Test
    void testAllSameNumbers() {
        Integer[] input = {7, 7, 7, 7};
        String result = summarizer.summarize(input);
        assertEquals("7, 7, 7, 7", result);
    }
    
    @Test
    void testSingleElementAtEnd() {
        Integer[] input = {1, 2, 3, 5};
        String result = summarizer.summarize(input);
        assertEquals("1-3, 5", result);
    }
    
    @Test
    void testSingleElementAtBeginning() {
        Integer[] input = {1, 3, 4, 5};
        String result = summarizer.summarize(input);
        assertEquals("1, 3-5", result);
    }
    
    @Test
    void testLargeRange() {
        Integer[] input = new Integer[100];
        for (int i = 0; i < 100; i++) {
            input[i] = i;
        }
        String result = summarizer.summarize(input);
        assertEquals("0-99", result);
    }
    
    @Test
    void testFormatRangeSingle() {
        String result = summarizer.formatRange(5, 5);
        assertEquals("5", result);
    }
    
    @Test
    void testFormatRangeMultiple() {
        String result = summarizer.formatRange(1, 5);
        assertEquals("1-5", result);
    }
    
    @Test
    void testFormatRangeNegative() {
        String result = summarizer.formatRange(-3, -1);
        assertEquals("-3--1", result);
    }
    
    // Edge case: null input (you might want to handle this in your method)
    @Test
    void testNullInput() {
        assertThrows(NullPointerException.class, () -> {
            summarizer.summarize(null);
        });
    }
    
    @Test
    void testSingleElementArrayWithNull() {
        Integer[] input = {null};
        assertThrows(NullPointerException.class, () -> {
            summarizer.summarize(input);
        });
    }
    
    @Test
    void testArrayWithNullInMiddle() {
        Integer[] input = {1, 2, null, 4};
        assertThrows(NullPointerException.class, () -> {
            summarizer.summarize(input);
        });
    }

        
        
}