package numberrangesummarizer;

import java.util.*;

/**
 * Implementation of NumberRangeSummarizer that processes comma-separated numbers
 * and groups consecutive sequences into ranges.
 */
public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {
    
    @Override
    public Collection<Integer> collect(String input) {
    	// checks if the input is empty
        if (input == null || input.isBlank()) {
        	return Collections.emptyList();   
            
        }
        
        try {
        	
            Set<Integer> numbers = new TreeSet<>();
            // treeset data structure to store the integers. we favour tree sets as they naturally implement
            // interface set behaviour of no duplicates, and keep the naturally ordering of the numbers 
            // which we will need since we will need as the implementation of summarizer
            for (String token : input.split(",")) { // we split the string input into an array of string tokens and iterate through the tokens
                String cleaned = token.strip(); // we strip the token of trailing and leading white spaces
                if (!cleaned.isEmpty()) {
                    numbers.add(Integer.parseInt(cleaned));
                }
            }
            
            return numbers;
            
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse numbers from: " + input, e);
        }
    }
    
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        // basic edge case- check if the integer is not empty
        if (input == null || input.isEmpty()) {
            return "";
        }
        
        // Input is already sorted and unique from our collect() method (TreeSet)
        Integer[] numbers = input.toArray(Integer[]::new);
        
        return summarize(numbers);
        
    }
    
    
    /**
     * Builds the summary string by detecting consecutive number sequences.
     * The numbers are then summarized
     */
    String summarize(Integer[] values) {
        List<String> summary = new ArrayList<>();
        int start = values[0];   // beginning of current run
        
        // we start iterating from count = 1
        for (int i = 1; i <= values.length; i++) {
            // We define a breakpoint. We want to iterate through the interger values and find consecutive pairs
        	// the values are already sorted. so we just check to see 
        	// if the values are no longer consecutive i.e val[i] != val[i-1]+1 as we begin forward or 
        	// we have reached the end of our list. That will be our breaking points
        	
        	
           boolean breakPoint = (i == values.length) || (values[i] != values[i - 1] + 1);
            
            if (breakPoint) {
            	//  if we are at breakpoint, we must assign the end of the sequence to the previous value
            	// as the current value is no longer a consecutive addition of the previous value
                int end = values[i - 1];   // end of the current run
                
                // format helper function
                summary.add(formatRange(start, end));
                // start a new sequence
                if (i < values.length) {
                    start = values[i]; // reset start for next run
                }
            }
        }
        return String.join(", ", summary);
    }
     String formatRange(int first, int last) {
        if (first == last) {
            return Integer.toString(first); // single number
        }
        return first + "-" + last; // range
    }
    
    public static void main(String[] args) {
        NumberRangeSummarizerImpl implementation = new NumberRangeSummarizerImpl();
        
        System.out.println("=== Number Range Summarizer ===");
        System.out.println("Please enter numbers separated by commas:");
        System.out.println("Example: 1,3,6,7,8,12,13,14,15,21,22,23,24,31");
        System.out.print("Input: ");
        
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput = scanner.nextLine();
            
            Collection<Integer> numbers = implementation.collect(userInput);
            String summary = implementation.summarizeCollection(numbers);
            
            System.out.println("\n--- Results ---");
            System.out.println("Original input: " + userInput);
            System.out.println("Parsed numbers: " + numbers);
            System.out.println("Range summary: " + summary);
            
        } catch (Exception error) {
            System.err.println("Error processing input: " + error.getMessage());
            System.err.println("Please make sure you entered valid numbers separated by commas.");
        }
    }
    
}