package com.congklak.core;

import java.util.ArrayList;

public class Solution implements Comparable<Solution>
{
	public ArrayList<Integer> picks;
	public int result;
	
	@Override
	public int compareTo(Solution o) {
		return this.result == o.result ? 0 :
			this.result < o.result ? 1 : -1;
	}
}