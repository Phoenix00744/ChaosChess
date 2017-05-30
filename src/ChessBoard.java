import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ChessBoard{

	private ChessSquarePanel[][] panels;
	private boolean panelPicked;
	private Color colorTurn;
	private ArrayList<ChessPiece> checkingPieces;
	private Chess chessGame;
	public ChessBoard(Chess game)
	{
		chessGame = game;
		colorTurn = Color.white;
		panelPicked = false;
		checkingPieces = new ArrayList<ChessPiece>();
		panels = new ChessSquarePanel[8][8];
		for(int i = 1; i<=8; i++)
		{
			for(int j = 1; j<=8; j++)
			{
				
				ChessPiece add = null;
				
				Color color;
				
				if(((i+j)% 2) != 0) 
					color = Color.BLACK;
				else
					color = Color.WHITE;
				
				panels[i-1][j-1] = new ChessSquarePanel(new Position(i, j), color, add, this);

				if(i == 1 || i == 8)
				{
					Color pieceColor;
					if(i == 8)
						pieceColor = Color.WHITE;
					else
						pieceColor = Color.BLACK;
					if(j == 1 || j == 8)
					{
						add = new Rook(pieceColor, new Position(i, j), this);
					}
					else if(j == 2 || j == 7)
					{
						add = new Knight(pieceColor, new Position(i, j), this);
					}
					else if(j == 3 || j == 6)
					{
						add = new Bishop(pieceColor, new Position(i, j), this);
					}
					else if(j == 4)
					{
						add = new Queen(pieceColor, new Position(i, j), this);
					}
					else
					{
						add = new King(pieceColor, new Position(i, j), this);
					}
				}
				
				else if(i == 2 || i == 7)
				{					
					if(i == 7)
						add = new Pawn(Color.WHITE, new Position(i, j), this);
					else
						add = new Pawn(Color.BLACK, new Position(i, j), this);
				}
				
				panels[i-1][j-1].setChessPiece(add);
			}
		}
	}
	
	public boolean isAPanelPicked()
	{
		return panelPicked;
	}
	
	public void setPanelPicked(boolean state)
	{
		panelPicked = state;
	}
	
	public ChessSquarePanel getPanel(int r, int c)
	{
		return panels[r-1][c-1];
	}
	
	public ChessSquarePanel getPickedPanel()
	{

		for(int i = 1; i <= 8; i++)
		{
			for(int j = 1; j<= 8; j++)
			{
				if(panels[i-1][j-1].isPicked())
					return panels[i-1][j-1];
			}
		}
		return null;
	}
	
	public boolean pieceBetween(Position pos1, Position pos2)
	{
		int dx = pos1.getRow() - pos2.getRow();
		int dy = pos1.getCol() - pos2.getCol();
		if(Math.abs(dx) < 2 && Math.abs(dy) < 2)
			return false;
		if(dx == 0)
		{
			if(dy > 0)
			{
				for(int i = 1; i < dy; i++)
				{
					if (getPanel(pos1.getRow(), pos1.getCol() - i).getChessPiece() != null)
						return true;
				}
			}
			else
			{
				for(int i = -1; i > dy; i--)
				{
					if(getPanel(pos1.getRow(), pos1.getCol() - i).getChessPiece() != null)
						return true;
				}
			}	
		}
		
		else if(dy == 0)
		{
			if(dx > 0)
			{
				for(int i = 1; i < dx; i++)
				{
					if(getPanel(pos1.getRow() - i, pos1.getCol()).getChessPiece() != null)
						return true;
				}
			}
			else
			{
				for(int i = -1; i > dx; i--)
				{
					if(getPanel(pos1.getRow() - i, pos1.getCol()).getChessPiece() != null)
						return true;
				}
			}	
		}
		
		else
		{
			for(int i = 1; i < Math.abs(dx); i++)
			{
				if(getPanel(pos1.getRow() - i*dx/Math.abs(dx), pos1.getCol() - i*dy/Math.abs(dy)).getChessPiece() != null)
					return true;
			}
		}
		
		return false;
	}
	
	public Color getColorTurn()
	{
		return colorTurn;
	}
	
	public void setColorTurn(Color color)
	{
		colorTurn = color;
	}
	
	public ArrayList<ChessPiece> getAllBlackPieces()
	{
		ArrayList<ChessPiece> blackPieces = new ArrayList<ChessPiece>();
		for(ChessSquarePanel[] pan1: panels)
		{
			for(ChessSquarePanel pan2: pan1)
			{
				if(pan2.getChessPiece()!= null && pan2.getChessPiece().getColor() == Color.BLACK)
					blackPieces.add(pan2.getChessPiece());

			}
		}
		
		return blackPieces;
	}
	
	public ArrayList<ChessPiece> getAllWhitePieces()
	{
		ArrayList<ChessPiece> whitePieces = new ArrayList<ChessPiece>();
		for(ChessSquarePanel[] pan1: panels)
		{
			for(ChessSquarePanel pan2: pan1)
			{
				if(pan2.getChessPiece() != null && pan2.getChessPiece().getColor() == Color.WHITE)
					whitePieces.add(pan2.getChessPiece());
			}
		}
		
		return whitePieces;
	}
	
	public King getBlackKing()
	{
		ArrayList<ChessPiece> blackPieces = getAllBlackPieces();
		for(ChessPiece piece : blackPieces)
		{
			if(piece instanceof King)
				return (King)piece;
		}
		return null;
	}
	
	public King getWhiteKing()
	{
		ArrayList<ChessPiece> whitePieces = getAllWhitePieces();
		for(ChessPiece piece : whitePieces)
		{
			if(piece instanceof King)
				return (King)piece;
		}
		return null;
	}
	
	public ArrayList<ChessPiece> getCheckingPieces()
	{
		return checkingPieces;
	}
	
	public void setCheckingPieces()
	{
		removeCheckingPieces();
		ArrayList<ChessPiece> pieces;
		King king;
		
		if(colorTurn.equals(Color.BLACK))
		{
			pieces = getAllWhitePieces();
			king = getBlackKing();
		}
		else
		{
			pieces = getAllBlackPieces();
			king = getWhiteKing();
		}

		Position kingPos = king.getPos();
		for(ChessPiece p: pieces)
		{
			if(p.canMoveTo(kingPos))
				checkingPieces.add(p);
		}
	}
	
	public void removeCheckingPieces()
	{
		checkingPieces = new ArrayList<ChessPiece>();
	}
	
	public boolean blackHasWon()
	{
		setCheckingPieces();
		if(checkingPieces.size() != 0 && whiteCannotMove())
			return true;
		return false;
	}
	
	public boolean whiteHasWon()
	{
		setCheckingPieces();
		if(checkingPieces.size() != 0 && blackCannotMove())
			return true;
		return false;
	}
	
	public boolean staleMate()
	{
		setCheckingPieces();
		if(colorTurn.equals(Color.BLACK) && checkingPieces.size() == 0 && blackCannotMove())
			return true;
		if(colorTurn.equals(Color.WHITE) && checkingPieces.size() == 0 && whiteCannotMove())
			return true;
		
		return false;
	}
	private boolean blackCannotMove()
	{
		ArrayList<ChessPiece> blackPieces = getAllBlackPieces();
		for(ChessPiece blackPiece: blackPieces)
		{
			for(int i = 1; i <= 8; i++)
			{
				for(int j = 1; j <= 8; j++)
				{
					Position pos = new Position(i, j);
					if(blackPiece.canMakeMoveTo(pos))
						return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean whiteCannotMove()
	{
		ArrayList<ChessPiece> whitePieces = getAllWhitePieces();
		for(ChessPiece whitePiece: whitePieces)
		{
			for(int i = 1; i <= 8; i++)
			{
				for(int j = 1; j <= 8; j++)
				{
					Position pos = new Position(i, j);
					if(whitePiece.canMakeMoveTo(pos))
						return false;
				}
			}
		}
		
		return true;
	}
	
	public void gameOver(String message)
	{
		JFrame gameOverWindow = new JFrame("Game Over");
		gameOverWindow.setBounds(385, 425, 270, 150);
		gameOverWindow.setResizable(true);
		gameOverWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container c = gameOverWindow.getContentPane(); 
		JPanel buttonPanel = new JPanel();
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		yes.addActionListener(new YesNoListener(gameOverWindow));
		no.addActionListener(new YesNoListener(gameOverWindow));
		buttonPanel.add(yes);
		buttonPanel.add(no);
		c.add(buttonPanel, BorderLayout.SOUTH);
		JLabel msg = new JLabel(message + "Play Again?", JLabel.CENTER);
		c.add(msg, BorderLayout.CENTER);
		gameOverWindow.setVisible(true);
	}
	
	private class YesNoListener implements ActionListener
	{
		private JFrame gameOverWindow;
		
		public YesNoListener(JFrame window)
		{
			gameOverWindow = window;
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			if(((JButton)e.getSource()).getText().equals("No"))
					System.exit(0);
			else
			{
				chessGame.newGame();
				gameOverWindow.dispose();
			}
		}
		
	}

	
}
