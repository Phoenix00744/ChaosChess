import java.awt.Color;


public class King extends ChessPiece{

	private boolean canCastle;

	public King(Color c, Position p, ChessBoard b)
	{
		super(c, p, b);
		canCastle = true;
	}
	
	public boolean canMoveTo(Position other) {
		// TODO Auto-generated method stub
		int dr = getPos().getRow() - other.getRow();
		int dc = getPos().getCol() - other.getCol();
		if((dr == -1 || dr == 0 || dr == 1) && (dc == -1 || dc == 0 || dc == 1) && !(dr == 0 && dc == 0))
		{
			ChessPiece takePiece = getBoard().getPanel(other.getRow(), other.getCol()).getChessPiece();
			if(takePiece != null)
				return !getColor().equals(takePiece.getColor());
			return true;
		}
		
		else if(getBoard().getCheckingPieces().size() == 0 && canCastle && dr == 0 && (dc == -2 || dc == 2))//castling
		{
			int col;
			if(dc < 0)
				col = 8;
			else
				col = 1;
			ChessPiece piece = getBoard().getPanel(other.getRow(), col).getChessPiece();
			if(piece != null && piece instanceof Rook && piece.getColor() == getColor() && ((Rook)piece).canCastle() 
					&& canMakeMoveTo(new Position(getPos().getRow(), getPos().getCol()-dc/2)))
				return !getBoard().pieceBetween(getPos(), other);
		}
		return false;
	}
	
	public boolean canMakeMoveTo(Position other)
	{
		return canMoveTo(other) && super.canMoveTo(other);
	}
	
	public boolean canCastle()
	{
		return canCastle;
	}
	
	public void setCanCastle(boolean state)
	{
		canCastle = state;
	}
}
