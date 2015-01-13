package com.ttt.mm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MiniMaxGame {

	public char[][] grid = new char[3][3];
	private List<Pair> availablePairs;
	char computer = 'x';
	char human = 'o';
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void showGrid() {
		System.out.println("\n==========");
		for (int i = 0; i < 3; i++) {
			System.out.print("|");
			for (int j = 0; j < 3; j++) {
				System.out.print(grid[i][j] + " |");
			}
			System.out.println("\n==========");
		}
	}

	public void startGame(int first) throws IOException {
		availablePairs = new ArrayList<Pair>();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				availablePairs.add(new Pair(i, j));
		showGrid();
		if (first == 0)	computerMove();
		else humanMove();
	}

	public void computerMove() throws IOException {
		Move bestMove;
		if(availablePairs.size()==9) bestMove = new Move(new Pair(0,0),0);
		else bestMove = getBestMove(0);
		playMove(bestMove.position, computer);
		int status = afterMove();
		if(status==1) humanMove();
	}

	private void humanMove() throws IOException {
		System.out.println("your move?");
		String[] ip = br.readLine().split(" ");
		int i = Integer.parseInt(ip[0]);
		int j = Integer.parseInt(ip[1]);
		playMove(new Pair(i-1, j-1), human);
		int status = afterMove();
		if(status==1) computerMove();
	}

	private int afterMove() {
		showGrid();
		if (isWin()){
			System.out.println("You lost");
			return 0;
		}	
		else if (isLoss()){
			System.out.println("You won");
			return 0;
		}
		else if (availablePairs.isEmpty()){
			System.out.println("Draw");
			return 0;
		}	
		return 1;
	}

	private void playMove(Pair position, char player) {
		grid[position.i][position.j] = player;
		availablePairs.remove(position);
	}

	private void undoMove(Pair position) {
		grid[position.i][position.j] = '\0';
		availablePairs.add(position);
	}

	private Move getBestMove(int depth) {
		Pair minPair = new Pair(), maxPair = new Pair();
		int minScore = 999, maxScore = -999;
		int score;
		char player = depth % 2 == 0 ? computer : human;
		//System.out.println("======At depth = " + depth + " , ======player = "	+ player + " ======");
		if (availablePairs.isEmpty())
			return new Move(new Pair(), 0);
		Iterator<Pair> it = availablePairs.iterator();
		Pair[] pairs = new Pair[availablePairs.size()];
		int i = -1;
		while (it.hasNext()) {
			pairs[++i] = it.next();
		}
		for (Pair pairForNextMove : pairs) {
			// System.out.println("----checking for pair "+pairForNextMove);
			playMove(pairForNextMove, player);
			// System.out.println("new grid is");
			// showGrid();
			score = getScore(depth);
			// System.out.println("--score recd is : "+score);
			if (score == 0) {
				Move mv = getBestMove(depth + 1);
				score = mv.score;
			}
			if (score < minScore) {
				minScore = score;
				minPair = pairForNextMove;
			}
			if (score > maxScore) {
				maxScore = score;
				maxPair = pairForNextMove;
			}
			undoMove(pairForNextMove);
			// System.out.println("--move undone: ");
			// System.out.println("grid back to");
			// showGrid();
		}
		if (player == computer) {
			Move m = new Move(maxPair, maxScore);
			// System.out.println("returning to depth : "+(depth-1)+", with move\n"+m);
			return m;
		} else {
			Move m = new Move(minPair, minScore);
			// System.out.println("returning to depth : "+(depth-1)+", with move\n"+m);
			return m;
		}
	}

	private int getScore(int depth) {
		if (isWin())
			return 10 - depth;
		if (isLoss())
			return depth - 10;
		return 0;
	}

	private boolean isWin() {
		for (int i = 0; i < 3; i++)
			if (grid[i][0] == computer && grid[i][1] == computer
					&& grid[i][2] == computer)
				return true;
		for (int i = 0; i < 3; i++)
			if (grid[0][i] == computer && grid[1][i] == computer
					&& grid[2][i] == computer)
				return true;
		if (grid[0][0] == computer && grid[1][1] == computer
				&& grid[2][2] == computer)
			return true;
		if (grid[0][2] == computer && grid[1][1] == computer
				&& grid[2][0] == computer)
			return true;
		return false;
	}

	private boolean isLoss() {
		for (int i = 0; i < 3; i++)
			if (grid[i][0] == human && grid[i][1] == human
					&& grid[i][2] == human)
				return true;
		for (int i = 0; i < 3; i++)
			if (grid[0][i] == human && grid[1][i] == human
					&& grid[2][i] == human)
				return true;
		if (grid[0][0] == human && grid[1][1] == human && grid[2][2] == human)
			return true;
		if (grid[0][2] == human && grid[1][1] == human && grid[2][0] == human)
			return true;
		return false;
	}

	public static void main(String[] args) throws IOException {
		MiniMaxGame game = new MiniMaxGame();
		int first = 0;
		System.out.println("Do you wanna go first ?y/n");
		String reply = game.br.readLine();
		if("yes".equals(reply)) first = 1;
		game.startGame(first);

	}

}
