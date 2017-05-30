import java.awt.Color;


public class Queen extends ChessPiece{

	public Queen(Color c, Position p, ChessBoard b)
	{
		super(c, p, b);
	}
	
	public boolean canMoveTo(Position other) {
		// TODO Auto-generated method stub
		if(other.equals(getPos()))
			return false;
		if((getPos().isDiagonalTo(other) || getPos().isHorizontalTo(other) || getPos().isVerticalTo(other)) 
				&& !getBoard().pieceBetween(getPos(), other))
		{
			ChessPiece takePiece = getBoard().getPanel(other.getRow(), other.getCol()).getChessPiece();
			if(takePiece != null)
				return !takePiece.getColor().equals(getColor());
			
			return true;
		}
		return false;
	}
	
	public boolean canMakeMoveTo(Position other)
	{
		return canMoveTo(other) && super.canMoveTo(other);
	}
}
