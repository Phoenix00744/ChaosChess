using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Chaos_Chess
{
    class Player
    {
        List<Piece> pawns, majorPieces;
        public Player(bool white, bool primary)
        {
            isWhite = white;
            isPrimary = primary;

            majorPieces = new List<Piece>();
            pawns = new List<Piece>();

            initialize_pieceSet();
        }

        bool isPrimary { get; set; }
        public bool isWhite { get; set; }
        public List<Piece> Pawns
        {
            get { return pawns; }
            private set { }
        }
        public List<Piece> MajorPieces
        {
            get { return majorPieces; }
            private set { }
        }

        public void add_or_remove_piece(bool add, Piece p)
        {
            if (Pawns.IndexOf(p) == -1)
            {
                Pawns.Remove(p);
            }
            else
            {
                MajorPieces.Remove(p);
            }
        }

        public void initialize_pieceSet()
        {
            string color_prefix;

            if(isWhite)
            {
                color_prefix = "White ";
            }
            else
            {
                color_prefix = "Black ";
            }

            if(isPrimary)
            {
                for(int i = 0; i < 8; i++)
                {
                    pawns.Add(new Piece(color_prefix + "Pawn", new Point(i,6), Side.Neither));
                }

                majorPieces = new List<Piece>()
                {
                    new Piece(color_prefix + "Rook", new Point(0,7), Side.QueenSide),
                    new Piece(color_prefix + "Rook", new Point(7,7), Side.KingSide),
                    new Piece(color_prefix + "Knight", new Point(1,7), Side.QueenSide),
                    new Piece(color_prefix + "Knight", new Point(6,7), Side.KingSide),
                    new Piece(color_prefix + "Bishop", new Point(2,7), Side.QueenSide),
                    new Piece(color_prefix + "Bishop", new Point(5,7), Side.KingSide),
                    new Piece(color_prefix + "Queen", new Point(3,7), Side.Neither),
                    new Piece(color_prefix + "King", new Point(4,7), Side.Neither),
                };
            }
            else
            {
                for (int i = 0; i < 8; i++)
                {
                    pawns.Add(new Piece(color_prefix + "Pawn", new Point(i,1), Side.Neither));
                }

                majorPieces = new List<Piece>()
                {
                    new Piece(color_prefix + "Rook", new Point(0,0), Side.QueenSide),
                    new Piece(color_prefix + "Rook", new Point(7,0), Side.KingSide),
                    new Piece(color_prefix + "Knight", new Point(1,0), Side.QueenSide),
                    new Piece(color_prefix + "Knight", new Point(6,0), Side.KingSide),
                    new Piece(color_prefix + "Bishop", new Point(2,0), Side.QueenSide),
                    new Piece(color_prefix + "Bishop", new Point(5,0), Side.KingSide),
                    new Piece(color_prefix + "Queen", new Point(3,0), Side.Neither),
                    new Piece(color_prefix + "King", new Point(4,0), Side.Neither),
                };
            }
        }
    }
}
