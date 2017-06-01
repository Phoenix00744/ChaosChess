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
        int boardDims;
        public MainWindow()
        {
            boardDims = 8;

            InitializeComponent();

            initialize_board();
            initialize_pieces();
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
                        square = new Tile(false, new Point(i,j));
                    }
                    else
                    {
                        square = new Tile(true, new Point(i,j));
                    }

                    place_rect(new Point(i, j), square);
                }
            }
        }

        void initialize_pieces()
        {
            Piece p = new Piece("Black Knight", new Point(2, 3));
            place_piece(p);
        }

        void place_rect(Point loc, Tile obj)
        {
            Grid.SetRow(obj, (int)loc.X);
            Grid.SetColumn(obj, (int)loc.Y);
            board.Children.Add(obj);
        }

        void place_piece(Piece piece)
        {
            Grid.SetRow(piece, (int)piece.Location.X);
            Grid.SetColumn(piece, (int)piece.Location.Y);
            board.Children.Add(piece);
        }

        public Image ImageFind(string name)
        {
            return new Image() { Source = (ImageSource)FindResource("White King") };
        }
    }
}