import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;

public class CompBoard extends GameObject {
	
	// ---------------------------------------------------------------------------------- Properties
	
	// Sets the number of rows and columns in each CompBoard
	public static final int ROWS = 3;
	public static final int COLUMNS = 3;
	
	private GamePiece[][] board;

	// ---------------------------------------------------------------------------------- Constructors
	
	// Workhorse
	public CompBoard(GamePiece[][] board, int x, int y, int width, int height) {
		setBoard(board);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	// Use this constructor generally
	public CompBoard(int x, int y, int width, int height) {
		this(createBoard(x, y, width, height), x, y, width, height);
	}
	
	// ---------------------------------------------------------------------------------- Methods
	
	// Creates initial CompBoard with blank GamePieces. Used in Constructor.
	private static GamePiece[][] createBoard(int x, int y, int width, int height) {
		GamePiece[][] board = new GamePiece[ROWS][COLUMNS];
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLUMNS; col++) {
				board[row][col] = new GamePiece(x+col*width/COLUMNS, y+row*height/ROWS, width/COLUMNS, height/ROWS);
			}
		}
		return board;
	}
	
	// Twists once counterclockwise
	@SuppressWarnings("unused")
	public void twist() {
		if(ROWS % 2 == 1 && ROWS == COLUMNS) {
			GamePiece[][] newBoard = cloneBoard();
			for(int i = 0; i < ROWS; i++) {
				for(int j = 0; j < COLUMNS; j++) {
					newBoard[i][j].setType(getBoard()[-j+COLUMNS/2*2][i].getType());
				}
			}
			setBoard(newBoard);
		} else {
			System.out.println("Cannot Twist!");
		}
	}
	
	public Point getPosition(Point p) {
		Point answer = new Point();
		int cellRow = (p.y-getY())*ROWS/getWidth();
		int cellColumn = (p.x-getX())*COLUMNS/getHeight();
		answer.x = (cellRow < 0 || cellRow >= ROWS) ? -1 : cellRow;
		answer.y = (cellColumn < 0 || cellColumn >= COLUMNS) ? -1 : cellColumn;
		return answer;
	}
	
	// Draws the CompBoard by drawing its lines and GamePieces.
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i <= ROWS; i++) {
			g.drawLine(getX() + i*getWidth()/ROWS, getY(), getX() + i*getWidth()/ROWS, getY()+getHeight());
		}
		for(int i = 0; i <= COLUMNS; i++) {
			g.drawLine(getX(), getY() + i*getHeight()/COLUMNS, getX()+getWidth(), getY() + i*getHeight()/COLUMNS);
		}
		for(GamePiece[] row : getBoard()) {
			for(GamePiece p : row) {
				if(p != null) {
					p.draw(g);
				}
			}
		}
	}

	public CompBoard clone() {
		// Incorrect. Need to clone board.
		return new CompBoard(getBoard(), getX(), getY(), getWidth(), getHeight());
	}
	
	public GamePiece[][] cloneBoard() {
		GamePiece[][] newboard = new GamePiece[ROWS][COLUMNS];
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				newboard[i][j] = getBoard()[i][j].clone();
			}
		}
		return newboard;
	}
	
	// ---------------------------------------------------------------------------------- Getters and Setters
	
	public GamePiece[][] getBoard() {
		return board;
	}

	public void setBoard(GamePiece[][] board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "CompBoard [board=" + Arrays.toString(board) + "] " + super.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof CompBoard))
			return false;
		CompBoard other = (CompBoard) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}
	
		
	
}
