/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        if (list.size() == 1) {
        	return list;
        }
        List<T> t1 = new ArrayList<T>();
        List<T> t2 = new ArrayList<T>();
        for (int i = 0; i < list.size() / 2; i++) {
        	t1.add(list.get(i));
        }
        for (int i = list.size()/2; i < list.size(); i++) {
        	t2.add(list.get(i));
        }
        List<T> st1 = mergeSort(t1, comparator);
        List<T> st2 = mergeSort(t2, comparator);

        int st1size = 0;
        int st2size = 0;
        List<T> result = new ArrayList<T>();
        while (!st1.isEmpty() && !st2.isEmpty()) {
        	T st1Val = st1.get(0);
        	T st2Val = st2.get(0);
        	int compare = comparator.compare(st1Val, st2Val);
        	if (compare <= 0) {
        		result.add(st1Val);
        		st1.remove(0);
        	}
        	else {
        		result.add(st2Val);
        		st2.remove(0);
        		st2size++;
        	}
        }

        if (st1.isEmpty()) {
        	result.addAll(st2);
        }
        else {
        	result.addAll(st1);
        }
        return result;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> heap = new PriorityQueue<T>(comparator);
        for (T el: list) {
        	heap.offer(el);
        }
        list.clear();
        while(!heap.isEmpty()) {
        	list.add(heap.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(k, comparator);
		for(T el: list) {
			if (heap.size() < k) {
				heap.offer(el);
			}
			else {
				if (comparator.compare(el, heap.peek()) > 0) {
					heap.poll();
					heap.offer(el);
				}
			}
		}
		
		List<T> result = new ArrayList<T>();
		while (!heap.isEmpty()) {
			result.add(heap.poll());
		}
		return result;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
