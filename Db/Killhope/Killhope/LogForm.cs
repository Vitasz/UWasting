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
    public partial class LogForm : Form
    {
        public static string connect = "Provider=Microsoft.JET.OLEDB.4.0; Data Source = db\\Uwaste.mdb";
        private OleDbConnection myCon;
        public LogForm()
        {
            InitializeComponent();
        }

        private void EnterBT_Click(object sender, EventArgs e)
        {
            if (LoginTXT.Text == "")
            {
                MessageBox.Show("Введите логин!");
                return;
            }
            else if (PasswordTXT.Text == "")
            {
                MessageBox.Show("Введите пароль!");
                return;
            }
            else
            {
                myCon = new OleDbConnection(connect);

                myCon.Open();

                string loginUsr = LoginTXT.Text;
                string passwordUsr = PasswordTXT.Text;
                string query = "SELECT id, Login, Password FROM `Users` WHERE `Login` = @log AND `Password` = @pass";
                DataTable table = new DataTable();
                OleDbDataAdapter adapt = new OleDbDataAdapter();
                OleDbCommand com = new OleDbCommand(query, myCon);
                com.Parameters.AddWithValue("@log", loginUsr);
                com.Parameters.AddWithValue("@pass", passwordUsr);
                adapt.SelectCommand = com;
                adapt.Fill(table);
                if (table.Rows.Count > 0)
                {
                    OleDbDataReader reader = com.ExecuteReader();
                    if (reader.HasRows)
                    {
                        while (reader.Read())
                        {
                            GlobalData.userID = Convert.ToInt32(reader[0]);
                        }
                        MessageBox.Show("Добро пожаловать!");
                        MainForm main = new MainForm();
                        main.Show();
                        Hide();
                    }
                    reader.Close();
                    myCon.Close();
                }
                else
                {
                    MessageBox.Show("Проверьте логин или пароль!");
                    LoginTXT.Clear();
                    PasswordTXT.Clear();
                    return;
                }
            }
        }

        private void RegistrationBT_Click(object sender, EventArgs e)
        {
            RegForm reg = new RegForm();
            reg.Show();
        }
    }
}
