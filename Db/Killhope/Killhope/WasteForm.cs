using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Killhope
{
    public partial class WasteForm : Form
    {
        private OleDbConnection myCon;
        public WasteForm()
        {
            InitializeComponent();
        }

        private void SaveBT_Click(object sender, EventArgs e)
        {
            myCon = new OleDbConnection(LogForm.connect);
            DataTable table = new DataTable();
            OleDbDataAdapter adapt = new OleDbDataAdapter();
            myCon.Open();
            string query = "INSERT INTO `Waste` (`Waste`, `Category`, `Date`, `User_id`) VALUES (@was, @cat, @date, @id)";
            OleDbCommand com = new OleDbCommand(query, myCon);
            com.Parameters.AddWithValue("@was", WasteTXT.Text);
            com.Parameters.AddWithValue("@cat", CatigotyTXT.Text);
            com.Parameters.AddWithValue("@date", DateTXT.Text);
            com.Parameters.AddWithValue("@id", GlobalData.userID);
            adapt.SelectCommand = com;
            adapt.Fill(table);
            myCon.Close();
        }

        private void WasteForm_Load(object sender, EventArgs e)
        {
            myCon = new OleDbConnection(LogForm.connect);
            myCon.Open();
            string query = "SELECT Waste, Category, Date FROM `Waste` WHERE `User_id` = @id";
            OleDbCommand com = new OleDbCommand(query, myCon);
            com.Parameters.AddWithValue("@id", GlobalData.userID);
            OleDbDataReader reader = com.ExecuteReader();
            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    WastedbTXT.Items.Add(reader[0].ToString());
                    CatTXT.Items.Add(reader[1].ToString());
                    DatedbTXT.Items.Add(reader[2].ToString());
                }
            }
            reader.Close();
            myCon.Close();
        }
    }
}
