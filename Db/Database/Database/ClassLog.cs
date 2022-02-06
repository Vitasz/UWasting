using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using System.Data.OleDb;
using System.Text;
using System.Threading.Tasks;

namespace Database
{
    public class ClassLog
    {
        private OleDbConnection myCon;

        public void join()
        {
            myCon = new OleDbConnection(Globaldata.connect);

            myCon.Open();

            string loginUsr = /*LoginTXT.Text*/;
            string passwordUsr = /*PasswordTXT.Text*/;
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
                        Globaldata.userID = Convert.ToInt32(reader[0]);
                    }
                    //MessageBox.Show("Добро пожаловать!");
                }
                reader.Close();
                myCon.Close();
            }
        }
    }
}
