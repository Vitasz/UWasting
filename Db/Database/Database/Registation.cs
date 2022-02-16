using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using Microsoft.Data.Sqlite;
using System.Text;
using System.Threading.Tasks;

namespace Database
{

    public static class Registration
    {
        public static bool CheckingUser(string login)
        {
            using var myCon = new SqliteConnection(Globaldata.connect);

            var command = myCon.CreateCommand();
            myCon.Open();
            string query = "SELECT Login FROM `Users` WHERE `Login` = $log";
            command.CommandText = query;
            command.Parameters.AddWithValue("$log", login);

            return command.ExecuteReader().HasRows;
        }
        public static bool Registrate(string login, string password, string name, string surname, int age)
        {
            using var myCon = new SqliteConnection(Globaldata.connect);
            if (CheckingUser(login))
                return false;
            string query = "INSERT INTO `Users` (`Login`, `Password`, `Name`, `Surname`) VALUES ($log, $pass, $name, $surname)";
            myCon.Open();
            var command = myCon.CreateCommand();
            command.CommandText = query;
            command.Parameters.AddWithValue("$log", login);
            command.Parameters.AddWithValue("$pass", password);
            command.Parameters.AddWithValue("$name", name);
            command.Parameters.AddWithValue("$surname", surname);
            command.ExecuteNonQuery();
            return true;
        }
        public static bool ChangeNameSurname(int id, string name, string surname)
        {
            using var myCon = new SqliteConnection(Globaldata.connect);

            myCon.Open();
            string query = "UPDATE `Users` SET `Name` = $name, `Surname` = $surname WHERE [id] = $id";
            var command = myCon.CreateCommand();
            command.CommandText = query;
            command.Parameters.AddWithValue("$name", name);
            command.Parameters.AddWithValue("$surname", surname);
            command.Parameters.AddWithValue("$id", id);
            command.ExecuteNonQuery();
            return true;
        }
        public static bool ChangeLogin(int id, string login)
        {
            using var myCon = new SqliteConnection(Globaldata.connect);

            myCon.Open();
            string query = "UPDATE `Users` SET `Login` = $login WHERE [id] = $id";
            var command = myCon.CreateCommand();
            command.CommandText = query;
            command.Parameters.AddWithValue("$login", login);
            command.Parameters.AddWithValue("$id", id);

            command.ExecuteNonQuery();
            return true;
        }
        public static bool ChangePassword(int id, string password)
        {
            using var myCon = new SqliteConnection(Globaldata.connect);

            myCon.Open();
            string query = "UPDATE `Users` SET `Password` = $password WHERE [id] = $id";
            var command = myCon.CreateCommand();
            command.CommandText = query;
            command.Parameters.AddWithValue("$password", password);
            command.Parameters.AddWithValue("$id", id);

            command.ExecuteNonQuery();
            return true;
        }
    }
}