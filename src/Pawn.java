import java.awt.Color;


public class Pawn extends ChessPiece{
	
	public Pawn(Color c, Position p, ChessBoard b)
	{
		super(c, p, b);
	}
	
	public boolean canMoveTo(Position other) {
		// TODO Auto-generated method stub
		if(getPos().isVerticalTo(other))
		{
			if(getBoard().getPanel(other.getRow(), other.getCol()).getChessPiece()!= null)
				return false;
			
			if(getColor().equals(Color.WHITE))
			{
				if(getPos().getRow() == 7)
				{
					return (other.getRow() == 6 || other.getRow() == 5);
				}
				return (other.getRow() == getPos().getRow() - 1);
			}
			else
			{
				if(getPos().getRow() == 2)
				{
					return (other.getRow() == 3 || other.getRow() == 4);
				}
				
				return (other.getRow() == getPos().getRow() + 1);
			}
		}
		else if(getPos().isDiagonalTo(other))
		{
			ChessPiece takePiece = getBoard().getPanel(other.getRow(), other.getCol()).getChessPiece();
			if(takePiece != null && !takePiece.getColor().equals(getColor()))
			{
				if(getColor().equals(Color.WHITE))
				{
					return (other.getRow() == getPos().getRow() - 1);
				}
				else
					return (other.getRow() == getPos().getRow() + 1);
					
			}
		}
		return false;
	}
	
	
	public boolean canMakeMoveTo(Position other)
	{
		return canMoveTo(other) && super.canMoveTo(other);
	}
}
