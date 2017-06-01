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
    /// Interaction logic for Tile.xaml
    /// </summary>
    public partial class Tile : UserControl
    {
        bool isWhite;
        public Tile(bool white, Point loc)
        {
            InitializeComponent();

            isWhite = white;
            Location = loc;

            if (isWhite)
            {
                tile.Background = new SolidColorBrush(Colors.White);
            }
            else
            {
                tile.Background = new SolidColorBrush(Colors.SlateGray);
            }
        }

        public Point Location { get; private set; }
    }
}
