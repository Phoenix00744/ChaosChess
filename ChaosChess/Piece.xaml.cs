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

namespace ChaosChess
{
    /// <summary>
    /// Interaction logic for Piece.xaml
    /// </summary>
    public partial class Piece : UserControl
    {
        ImageSource sprite;

        public Piece(string p, Point loc)
        {
            Location = loc;

            InitializeComponent();

            sprite = piece_setup(p);
            piece.Background = new ImageBrush(sprite);
        }

        public bool isWhite { get; private set; }
        public Point Location { get; private set; }

        ImageSource piece_setup(string piece)
        {
            string color = piece.Split(' ')[0];
            MessageBox.Show(color);
            isWhite = color.Trim() == "White" ? true : false;

            return (ImageSource)FindResource(piece);
        }
    }
}
