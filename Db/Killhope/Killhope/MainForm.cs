using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Killhope
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private void WasteBT_Click(object sender, EventArgs e)
        {
            WasteForm frm = new WasteForm();
            frm.Show();
        }

        private void IncomeBT_Click(object sender, EventArgs e)
        {
            IncomeForm frm = new IncomeForm();
            frm.Show();
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            Application.Exit();
        }
    }
}
