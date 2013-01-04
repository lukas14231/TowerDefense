package model;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

public class LevelGenerator {

	Random rnd = new Random();
	int counter;
	Vector<Point> testPath;
	char[][] gameField;
	char[][] gField;

	public LevelGenerator() {

	}

	public int getCounter() {
		return counter;
	}

	public char[][] getNewLevel(int cols, int rows) {
		createNewLevel(cols, rows);
		return gField;
	}

	public void createNewLevel(int cols, int rows) {
		createEmptyGameField(cols, rows);
		counter = 1;
		testPath = new Vector<Point>();
		int tempCol = rnd.nextInt(cols);
		int tempRow = rnd.nextInt(rows);
		rndLevelBuilder(tempCol, tempRow, cols, rows);
		gField = new char[cols + 2][rows + 2];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				gField[i + 1][j + 1] = gameField[i][j];
			}
		}
		for (int k = 0; k < cols + 2; k++) {
			gField[k][0] = '#';
			gField[k][rows + 1] = '#';
		}
		for (int l = 0; l < rows + 2; l++) {
			gField[0][l] = '#';
			gField[cols + 1][l] = '#';
		}
	}

	private void createEmptyGameField(int cols, int rows) {
		gameField = new char[cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				gameField[i][j] = '_';
			}
		}
	}

	private void rndLevelBuilder(int col, int row, int cols, int rows) {
		boolean[] options = getOptions(col, row, cols, rows, gameField);
		if (counter > 0.3 * ((double) cols * rows)) {
			gameField[col][row] = 'X';
			gameField[(int) testPath.firstElement().getX()][(int) testPath
					.firstElement().getY()] = 'S';
		} else if (options[0] == true || options[1] == true
				|| options[2] == true || options[3] == true) {
			int choice = choseRandomDirection(options);
			int steps = rnd.nextInt(3) + 1;
			if (choice == 0)
				// i = 0, weil aktuelles Feld korrekte Richtung bekommt
				for (int i = 0; i <= steps; i++) {
					gameField[col][row - i] = '^';
					testPath.addElement(new Point(col, row - i));
				}
			else if (choice == 1)
				for (int i = 0; i <= steps; i++) {
					gameField[col + i][row] = '>';
					testPath.addElement(new Point(col + i, row));
				}
			else if (choice == 2)
				for (int i = 0; i <= steps; i++) {
					gameField[col][row + i] = 'v';
					testPath.addElement(new Point(col, row + i));
				}
			else if (choice == 3)
				for (int i = 0; i <= steps; i++) {
					gameField[col - i][row] = '<';
					testPath.addElement(new Point(col - i, row));
				}
			counter += steps;
			rndLevelBuilder((int) testPath.lastElement().getX(), (int) testPath
					.lastElement().getY(), cols, rows);
		} else if (counter > 0 * ((double) cols * rows)) {
			gameField[col][row] = 'X';
			gameField[(int) testPath.firstElement().getX()][(int) testPath
					.firstElement().getY()] = 'S';
		} else
			createNewLevel(cols, rows);
	}

	private int choseRandomDirection(boolean[] options) {
		int rndNumber;
		Vector<Integer> temp = new Vector<Integer>();
		{
			for (int i = 0; i <= 3; i++) {
				if (options[i])
					temp.addElement(i);
			}
		}
		rndNumber = rnd.nextInt(temp.size());

		return temp.elementAt(rndNumber);
	}

	/*
	 * private boolean[] getOptions(int col, int row, int cols, int rows,
	 * char[][] gameField) { boolean[] options = new boolean[] { false, false,
	 * false, false };
	 * 
	 * if (row - 3 >= 0 && noObstacle(gameField[col][row - 1]) &&
	 * noObstacle(gameField[col][row - 2]) && noObstacle(gameField[col][row -
	 * 3])) { if (testPath.size() > 2) { if (notMeleeRange(col, row - 1,
	 * testPath.firstElement()) && notMeleeRange(col, row - 2,
	 * testPath.firstElement()) && notMeleeRange(col, row - 3,
	 * testPath.firstElement())) options[0] = true; } } if (col + 3 < cols &&
	 * noObstacle(gameField[col + 1][row]) && noObstacle(gameField[col +
	 * 2][row]) && noObstacle(gameField[col + 3][row])) { if (testPath.size() >
	 * 2) { if (notMeleeRange(col + 1, row, testPath.firstElement()) &&
	 * notMeleeRange(col + 2, row, testPath.firstElement()) && notMeleeRange(col
	 * + 3, row, testPath.firstElement())) options[0] = true; } } if (row + 3 <
	 * rows && noObstacle(gameField[col][row + 1]) &&
	 * noObstacle(gameField[col][row + 2]) && noObstacle(gameField[col][row +
	 * 3])) { if (testPath.size() > 2) { if (notMeleeRange(col, row + 1,
	 * testPath.firstElement()) && notMeleeRange(col, row + 2,
	 * testPath.firstElement()) && notMeleeRange(col, row + 3,
	 * testPath.firstElement())) options[0] = true; } } if (col - 3 >= 0 &&
	 * noObstacle(gameField[col - 1][row]) && noObstacle(gameField[col -
	 * 2][row]) && noObstacle(gameField[col - 3][row])) { if (testPath.size() >
	 * 2) { if (notMeleeRange(col - 1, row, testPath.firstElement()) &&
	 * notMeleeRange(col - 2, row, testPath.firstElement()) && notMeleeRange(col
	 * - 3, row, testPath.firstElement())) options[0] = true; } }
	 * 
	 * return options; }
	 */

	private boolean noObstacle(char c) {
		boolean tempBool = true;
		if (c != '_')
			tempBool = false;
		return tempBool;
	}

	private boolean[] getOptions(int col, int row, int cols, int rows,
			char[][] gameField) {
		boolean[] options = new boolean[] { false, false, false, false };
		if (row - 3 >= 0 && noObstacle(gameField[col][row - 1])
				&& noObstacle(gameField[col][row - 2])
				&& noObstacle(gameField[col][row - 3]))
			options[0] = true;
		if (col + 3 < cols && noObstacle(gameField[col + 1][row])
				&& noObstacle(gameField[col + 2][row])
				&& noObstacle(gameField[col + 3][row]))
			options[1] = true;
		if (row + 3 < rows && noObstacle(gameField[col][row + 1])
				&& noObstacle(gameField[col][row + 2])
				&& noObstacle(gameField[col][row + 3]))
			options[2] = true;
		if (col - 3 >= 0 && noObstacle(gameField[col - 1][row])
				&& noObstacle(gameField[col - 2][row])
				&& noObstacle(gameField[col - 3][row]))
			options[3] = true;
		return options;
	}

	private boolean notMeleeRange(int col, int row, Point p) {
		int dx = Math.abs(col - p.x);
		int dy = Math.abs(row - p.y);
		if (dx + dy < 3)
			return false;
		else
			return true;
	}
}
/*
 * private boolean noObstacle(char c) { boolean tempBool = true; if (c == '^' ||
 * c == 'v' || c == '<' || c == '>' || c == 'S') tempBool = false; return
 * tempBool;
 * 
 * }
 */
