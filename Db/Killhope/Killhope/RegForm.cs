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
    public partial class RegForm : Form
    {
        private OleDbConnection myCon;
        public RegForm()
        {
            InitializeComponent();
        }
        public Boolean CheckingUsers()
        {
            myCon = new OleDbConnection(LogForm.connect);

            myCon.Open();

            string query = "SELECT Login FROM `Users` WHERE `Login` = @log";
            DataTable table1 = new DataTable();
            OleDbDataAdapter adapt = new OleDbDataAdapter();
            OleDbCommand com = new OleDbCommand(query, myCon);
            com.Parameters.AddWithValue("@log", LoginTXT.Text);
            adapt.SelectCommand = com;
            adapt.Fill(table1);
            if (table1.Rows.Count > 0)
            {
                MessageBox.Show("Такой логин уже используется!");
                return true;
            }
            else
            {
                return false;
            }
        }
        private void RegistrationBT_Click(object sender, EventArgs e)
        {
            if (LoginTXT.Text == "")
            {
                MessageBox.Show("Введите логин!");
                return;
            }
            if (PasswordTXT.Text == "")
            {
                MessageBox.Show("Введите пароль!");
                return;
            }
            if (NameTXT.Text == "")
            {
                MessageBox.Show("Введите имя!");
                return;
            }
            if (SurnameTXT.Text == "")
            {
                MessageBox.Show("Введите фамилию!");
                return;
            }
            if (AgeTXT.Text == "")
            {
                MessageBox.Show("Введите возраст!");
                return;
            }
            if (PasswordSecTXT.Text == "")
            {
                MessageBox.Show("Введите пароль повторно!");
                return;
            }
            if (LoginTXT.Text.Length < 3 || LoginTXT.Text.Length > 40)
            {
                MessageBox.Show("Минимальная длина логина - 3 (Макс. - 40)!");
                return;
            }
            if (PasswordTXT.Text.Length < 6 || PasswordTXT.Text.Length > 40)
            {
                MessageBox.Show("Минимальная длина пароля - 6 (Макс. - 40)!");
                return;
            }
            if (PasswordSecTXT.Text.Length < 6 || PasswordSecTXT.Text.Length > 40)
            {
                MessageBox.Show("Минимальная длина пароля - 6 (Макс. - 40)!");
                return;
            }
            if (CheckingUsers())
            {
                return;
            }
            if (PasswordSecTXT.Text != PasswordSecTXT.Text)
            {
                MessageBox.Show("Пароли не совпадают!");
                PasswordTXT.Clear();
                PasswordSecTXT.Clear();
            }
            else
            {
                string query = "INSERT INTO `Users` (`Login`, `Password`, `Name`, `Surname`, `Age`) VALUES (@log, @pass, @name, @surname, @age)";
                OleDbCommand com = new OleDbCommand(query, myCon);
                com.Parameters.AddWithValue("@log", LoginTXT.Text);
                com.Parameters.AddWithValue("@pass", PasswordTXT.Text);
                com.Parameters.AddWithValue("@name", NameTXT.Text);
                com.Parameters.AddWithValue("@surname", SurnameTXT.Text);
                com.Parameters.AddWithValue("@age", AgeTXT.Text);
                com.ExecuteNonQuery();
                myCon.Close();
                MessageBox.Show("Вы успешно зарегистрировались!");
                Hide();
                Close();
            }
        }
    }
}
