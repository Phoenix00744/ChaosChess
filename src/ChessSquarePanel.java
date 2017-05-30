import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class ChessSquarePanel extends JPanel implements MouseListener{

	private static final long serialVersionUID = -3486667605553747895L;
	private Position p;
	private boolean picked;
	private Color c;
	private ChessPiece piece;
	private ChessBoard board;
	private ImageIcon image;
	
	public ChessSquarePanel(Position pos, Color color, ChessPiece chessPiece, ChessBoard b)
	{
		piece = chessPiece;
		p = pos;
		c = color;
		picked = false;
		board = b;
		String pictureName;
		if(piece != null)
		{
			pictureName = "assets/";
			if(piece.getColor().equals(Color.BLACK))
				pictureName += "Black";
			else
				pictureName += "White";
			
			if(piece instanceof Pawn)
				pictureName += "Pawn";
			else if(piece instanceof Rook)
				pictureName += "Rook";
			else if(piece instanceof Knight)
				pictureName += "Knight";
			else if(piece instanceof Bishop)
				pictureName += "Bishop";
			else if(piece instanceof Queen)
				pictureName += "Queen";
			else if(piece instanceof King)
				pictureName += "King";
			
			pictureName += ".png";
			image = new ImageIcon(pictureName);
		}
		this.addMouseListener(this);
		
	}
	
	public Position getPosition()
	{
		return p;
	}
	
	public boolean isPicked()
	{
		return picked;
	}
	
	public void setPicked(boolean state)
	{
		picked = state;
	}
	
	public ChessPiece getChessPiece()
	{
		return piece;
	}
	
	public void setChessPiece(ChessPiece chessPiece)
	{
		piece = chessPiece;
		String pictureName;
		
		if(piece != null)
		{
			pictureName = "assets/";
			if(piece.getColor().equals(Color.BLACK))
				pictureName += "Black";
			else
				pictureName += "White";
			
			if(piece instanceof Pawn)
				pictureName += "Pawn";
			else if(piece instanceof Rook)
				pictureName += "Rook";
			else if(piece instanceof Knight)
				pictureName += "Knight";
			else if(piece instanceof Bishop)
				pictureName += "Bishop";
			else if(piece instanceof Queen)
				pictureName += "Queen";
			else if(piece instanceof King)
				pictureName += "King";
			
			pictureName += ".png";
			image = new ImageIcon(pictureName);
		}
		else
			image = null;
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(picked)
			g.setColor(Color.YELLOW);
		else
			g.setColor(c);
		
		g.fillRect(0, 0, getWidth(), getHeight());
		if(image != null)
			image.paintIcon(this, g, 26, 26);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(!board.isAPanelPicked())
		{
			if(piece == null || piece.getColor() != board.getColorTurn())
				return;
			
			picked = true;
			board.setPanelPicked(true);
		}
		
		else
		{
			ChessSquarePanel panel = board.getPickedPanel();
			if(panel.piece != null && panel.piece.canMakeMoveTo(p) && board.getColorTurn().equals(panel.piece.getColor()))
			{					
				panel.piece.moveTo(p);
				
			}
			
			panel.picked = false;
			board.setPanelPicked(false);
			panel.repaint();
		}
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
