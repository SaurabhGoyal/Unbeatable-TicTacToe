package com.ttt.mm;

public class Move {
	@Override
	public String toString() {
		return "Move [position=" + position + ", score=" + score + "]";
	}
	Pair position;
	int score;
	public Move(){
		
	}
	public Move(Pair position, int score) {
		super();
		this.position = position;
		this.score = score;
	}
}
