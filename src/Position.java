
public class Position {

	private int r;
	private int c;
	
	public Position(int row, int col)
	{
		r = row;
		c = col;
	}
	
	public boolean isDiagonalTo(Position other)
	{
		return other.isInBounds() && (other.r-r == other.c - c || r-other.r == other.c - c);
	}
	
	public boolean isHorizontalTo(Position other)
	{
		return other.isInBounds() && other.r == r;
	}
	
	public boolean isVerticalTo(Position other)
	{
		return other.isInBounds() && other.c == c;
	}
	
	public boolean isKnightTo(Position other)
	{
		boolean result;
		int dx = Math.abs(r - other.r);
		int dy = Math.abs(c - other.c);
		result = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
		return other.isInBounds() && result;
	}
	
	public int getRow()
	{
		return r;
	}
	
	public int getCol()
	{
		return c;
	}
	
	public boolean isInBounds()
	{
		return r>0 && r<9 && c>=0 && c<9;
	}
	
	public boolean equals(Object other)
	{
		Position pos2 = (Position)other;
		return r == pos2.r && c == pos2.c;
	}
}
