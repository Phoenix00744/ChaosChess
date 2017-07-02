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
    public enum Side
    {
        QueenSide = -1,
        Neither,
        KingSide
    };

    public partial class Piece : UserControl
    {
        Communicator com;

        public Piece()
        {

        }

        private void Death(object sender, EventArgs e)
        {
            ((Grid)Parent).Children.Remove(this);
        }

        public Piece(string p, Point loc, Side s)
        {
            Location = loc;

            string[] nameSplit = p.Split(' ');
            string color = nameSplit[0];
            string type = nameSplit[1];
            isWhite = color.Trim() == "White" ? true : false;
            PieceName = type;

            InitializeComponent();

            piece.Background = new ImageBrush(piece_setup(p));
        }

        public bool isWhite { get; private set; }
        public Side Side { get; set; }
        public Point Location { get; set; }
        public string PieceName { get; private set; }

        ImageSource piece_setup(string piece)
        { 
            return (ImageSource)FindResource(piece);
        }

        private void Select(object sender, MouseButtonEventArgs e)
        {
            if (com.IsWhitePlayer == isWhite || com.readyToMove)
            {
                Background = new SolidColorBrush(Colors.DarkSeaGreen);
                com.selectedPiece = this;
            }
        }

        public void set_communicator(Communicator c)
        {
            com = c;
        }
    }
}
