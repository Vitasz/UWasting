using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using System.Data.OleDb;
using System.Text;
using System.Threading.Tasks;

namespace Database
{
    public static class Autorization
    {
        /// <summary>
        /// Поиск пользователя в бд по логину и паролю
        /// </summary>
        /// <param name="login">Логин</param>
        /// <param name="password">Пароль</param>
        /// <returns>Id пользователя</returns>
        public static (int id, string email, string Name, string Surname) Join(string login, string password)
        {
            using var myCon = new OleDbConnection(Globaldata.connect);

            myCon.Open();
            string query = "SELECT id, Login, Name, Surname FROM `Users` WHERE `Login` = @log AND `Password` = @pass";
            DataTable table = new();
            OleDbDataAdapter adapt = new();
            OleDbCommand com = new(query, myCon);
            com.Parameters.AddWithValue("@log", login);
            com.Parameters.AddWithValue("@pass", password);
            adapt.SelectCommand = com;
            adapt.Fill(table);
            if (table.Rows.Count > 0)
            {
                using OleDbDataReader reader = com.ExecuteReader();
                if (reader.HasRows)
                {
                    while (reader.Read())
                    {
                        int UserId = Convert.ToInt32(reader[0]);
                        string email = reader[1].ToString(), Name = reader[2].ToString(), Surname = reader[3].ToString();
                        return (UserId, email, Name, Surname);
                    }
                }

            }
            return (-1, "", "", "");
        }
    }
}
