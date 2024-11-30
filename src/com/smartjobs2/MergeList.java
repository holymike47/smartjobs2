package com.smartjobs2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeList {

	// create a generic method that take 2 arguments that can be sorted
	public static <T extends Comparable<T>> List<T> merge(List<T> list1, List<T> list2) {
		// define a result variable to hold the result;
		List<T> result = new ArrayList<>();
		// throw an exception if either of the supplied arguments are null;
		if (list1 == null || list2 == null) {
			throw new IllegalArgumentException("Null values not accepted");
		}
		// if either list is empty, add only the non-empty list to the result
		if (list1.isEmpty() && !list2.isEmpty()) {
			result.addAll(list2);
		} else if (list2.isEmpty() && !list1.isEmpty()) {
			result.addAll(list1);
		} else {
			// add both lists to the result if neither is empty
			result.addAll(list1);
			result.addAll(list2);
		}
		// sort the resulting collection if not already sorted
		result.sort(null);
		// return result to the caller
		return result;
	}

	public static void main(String[] args) {
		List<Integer> list1 = Arrays.asList(new Integer[] { 1, 3, 5, 7, 9, 17 });
		List<Integer> list2 = Arrays.asList(new Integer[] { 4, 8, 7, 11, 19 });
		// list1 = new ArrayList<>();
		var result = merge(list1, list2);
		System.out.println(result);

	}

}
