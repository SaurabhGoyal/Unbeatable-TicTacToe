package com.ttt.mm;

public class Pair {
	@Override
	public String toString() {
		return "Pair [i=" + i + ", j=" + j + "]";
	}
	int i,j;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		return true;
	}
	public Pair(){
		
	}
	public Pair(int i,int j){
		this.i = i;
		this.j = j;
	}
}
