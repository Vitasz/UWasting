using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using System.Text;
using System.Threading.Tasks;
using Npgsql;

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
            using var myCon = new NpgsqlConnection(Globaldata.connect);
            myCon.Open();

            var cmd = new NpgsqlCommand("SELECT \"id\", \"Login\", \"Name\", \"Surname\" FROM \"Users\" WHERE \"Login\" = @log AND \"Password\" = @pass", myCon)
            {
                Parameters =
                {
                    new("@log", login),
                    new("@pass", password)
                }
            };
            using (var reader = cmd.ExecuteReader())
            {
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
