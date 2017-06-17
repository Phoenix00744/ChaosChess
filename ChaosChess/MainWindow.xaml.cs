using Chaos_Chess;
using ChaosChess;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Chaos_Chess
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        Player primaryPlayer, opponent;
        Communicator com;
        Random selector;
        int boardDims;

        public MainWindow()
        {
            boardDims = 8;

            InitializeComponent();

            selector = new Random();
            primaryPlayer = selector.Next(2) == 0 ? new Player(true, true) : new Player(false, true);
            opponent = primaryPlayer.isWhite ? new Player(false, false) : new Player(true, false);

            com = new Communicator(primaryPlayer.isWhite);
            com.MoveCommand += MovePiece;
            com.Killing += PieceKilled;

            initialize_board();
            initialize_pieces();
        }

        private void PieceKilled(object sender, EventArgs e)
        {
            board.Children.Remove(com.dyingPiece);
        }

        private void MovePiece(object sender, EventArgs e)
        {
            board.Children.Remove(com.selectedPiece);
            place_piece(com.movedPiece);
        }

        void initialize_board()
        {
            Tile square;

            for (int i = 0; i < boardDims; i++)
            {
                for (int j = 0; j < boardDims; j++)
                {
                    if ((i + j + 2) % 2 == 1)
                    {
                        square = new Tile(false, new Point(i, j), com);
                    }
                    else
                    {
                        square = new Tile(true, new Point(i, j), com);
                    }

                    place_rect(new Point(i, j), square);
                }
            }
        }

        void initialize_pieces()
        {
            List<Piece> allPieces = new List<Piece>();
            allPieces.AddRange(primaryPlayer.Pawns);
            allPieces.AddRange(primaryPlayer.MajorPieces);
            allPieces.AddRange(opponent.Pawns);
            allPieces.AddRange(opponent.MajorPieces);

            foreach(Piece p in allPieces)
            {
                place_piece(p);
                p.set_communicator(com);
            }
        }

        void clear_pieces()
        {
            board.Children.Clear();
            initialize_board();
        }

        void place_rect(Point loc, Tile obj)
        {
            Grid.SetRow(obj, (int)loc.Y);
            Grid.SetColumn(obj, (int)loc.X);
            board.Children.Add(obj);
        }

        void place_piece(Piece piece)
        {
            Grid.SetRow(piece, (int)piece.Location.Y);
            Grid.SetColumn(piece, (int)piece.Location.X);
            board.Children.Add(piece);
        }

        //For debugging
        private void Move(object sender, RoutedEventArgs e)
        {

        }

        private void Start_Game(object sender, RoutedEventArgs e)
        {
            clear_pieces();
            initialize_pieces();
        }
    }
}