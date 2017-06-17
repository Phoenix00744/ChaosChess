using Chaos_Chess;
using System;
using System.Collections.Generic;
using System.Windows;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media;

namespace ChaosChess
{
    public class Communicator
    {
        public event EventHandler MoveCommand;
        public event EventHandler Killing;

        Piece selPiece, newPiece, diePiece;
        Point moveSelection;
        bool isWhitePlayer;

        public Communicator(bool playerColor)
        {
            selPiece = null;
            moveSelection = new Point();
            isWhitePlayer = playerColor;
        }

        public bool IsWhitePlayer
        {
            get { return isWhitePlayer; }
        }

        public Piece movedPiece
        {
            get { return newPiece; }
        }

        public Piece dyingPiece
        {
            get { return diePiece; }
        }

        public Piece selectedPiece
        {
            get { return selPiece; }
            set
            {
                if(selPiece != null && value.Location == selectedPiece.Location)
                {
                    selPiece.Background = new SolidColorBrush(Colors.Transparent);
                    selPiece = null;
                    readyToMove = false;
                }
                else
                {
                    if (readyToMove)
                    {
                        if (selPiece.isWhite == value.isWhite)
                        {
                            selPiece.Background = new SolidColorBrush(Colors.Transparent);
                        }
                        else
                        {
                            if (move_piece(value.Location, true))
                            {
                                diePiece = value;
                                Killing(this, new EventArgs());
                                return;
                            }
                            else
                            {
                                value.Background = new SolidColorBrush(Colors.Transparent);
                                return;
                            }
                        }
                    }
                    else
                    {
                        readyToMove = true;
                    }
                    selPiece = value;
                }
            }
        }

        public Point commandedMove
        {
            get { return moveSelection; }
            set
            {
                move_piece(value, false);
            }
        }

        public bool readyToMove
        {
            get;
            private set;
        }

        bool move_piece(Point moveValue, bool taking)
        {
            if (readyToMove && move_rule_check(taking, moveValue))
            {
                moveSelection = moveValue;
                newPiece = selectedPiece;
                newPiece.Location = moveSelection;
                newPiece.Background = new SolidColorBrush(Colors.Transparent);

                MoveCommand(this, new EventArgs());

                readyToMove = false;
                selPiece = null;

                return true;
            }
            else
            {
                moveSelection = new Point();

                return false;
            }
        }

        /* IMPLEMENT THE FOLLOWING MOVES:
         * --CASTLING
         * --EN PASSANT
         * --PAWN PROMOTION
         * */
        bool move_rule_check(bool take, Point move)
        {
            string selPieceType = selectedPiece.PieceName;
            double pieceXCorr = selectedPiece.Location.X;
            double pieceYCorr = selectedPiece.Location.Y;
            double transXDiff = move.X - pieceXCorr;
            double transYDiff = move.Y - pieceYCorr;

            switch(selPieceType)
            {
                case "Pawn":
                    if (transXDiff == 0 && transYDiff == -1 && !take) return true;
                    else if (pieceYCorr == 6 && transYDiff == -2 && !take) return true;
                    else if (take && ((transXDiff == -1 && transYDiff == -1) || (transXDiff == 1 && transYDiff == -1))) return true;
                    else return false;
                case "Knight":
                    if ((Math.Abs(transXDiff) == 2 && Math.Abs(transYDiff) == 1) || (Math.Abs(transXDiff) == 1 && Math.Abs(transYDiff) == 2)) return true;
                    else return false;
                case "Bishop":
                    if (Math.Abs(transYDiff / transXDiff) == 1) return true;
                    else return false;
                case "Rook":
                    if (Math.Abs(transXDiff) == 0 || Math.Abs(transYDiff) == 0) return true;
                    else return false;
                case "Queen":
                    if (Math.Abs(transYDiff / transXDiff) == 1 || (Math.Abs(transXDiff) == 0 || Math.Abs(transYDiff) == 0)) return true;
                    else return false;
                case "King":
                    if (Math.Abs(transXDiff) <= 1 && Math.Abs(transYDiff) <= 1) return true;
                    else return false;
            }

            return true;
        }
    }
}
