using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using System.Data.OleDb;
using System.Text;
using System.Threading.Tasks;

namespace Database
{

    public class ClassReg
    {
        private OleDbConnection myCon;
        public Boolean CheckingUsers()
        {
            myCon = new OleDbConnection(Globaldata.connect);

            myCon.Open();

            string query = "SELECT Login FROM `Users` WHERE `Login` = @log";
            DataTable table1 = new DataTable();
            OleDbDataAdapter adapt = new OleDbDataAdapter();
            OleDbCommand com = new OleDbCommand(query, myCon);
            com.Parameters.AddWithValue("@log", /*LoginTXT.Text*/);
            adapt.SelectCommand = com;
            adapt.Fill(table1);
            if (table1.Rows.Count > 0)
            {
                //MessageBox.Show("Такой логин уже используется!");
                return true;
            }
            else
            {
                return false;
            }
        }
        public void regist()
        {
            if (CheckingUsers())
            {
                return;
            }
            else
            {
                string query = "INSERT INTO `Users` (`Login`, `Password`, `Name`, `Surname`, `Age`) VALUES (@log, @pass, @name, @surname, @age)";
                OleDbCommand com = new OleDbCommand(query, myCon);
                com.Parameters.AddWithValue("@log", /*LoginTXT.Text*/);
                com.Parameters.AddWithValue("@pass", /*PasswordTXT.Text*/);
                com.Parameters.AddWithValue("@name", /*NameTXT.Text*/);
                com.Parameters.AddWithValue("@surname", /*SurnameTXT.Text*/);
                com.Parameters.AddWithValue("@age", /*AgeTXT.Text*/);
                com.ExecuteNonQuery();
                myCon.Close();
                //MessageBox.Show("Вы успешно зарегистрировались!");
            }
        }
    }
}