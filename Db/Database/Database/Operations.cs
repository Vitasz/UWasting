using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using Microsoft.Data.Sqlite;
using System.Text;
using System.Threading.Tasks;

namespace Database
{
    public static class Operations
    {
        public static void SaveOperation(int value, string category, DateTime date, int UserId)
        {
            using var myCon = new SqliteConnection(Globaldata.connect);
            var command = myCon.CreateCommand();
            myCon.Open();
            string query = "INSERT INTO `Operations` (`Value`, `Category`, `Date`, `User_id`) VALUES ($sum, $cat, $date, $id)";
            command.CommandText = query;
            command.Parameters.AddWithValue("$sum", value);
            command.Parameters.AddWithValue("$cat", category);
            command.Parameters.AddWithValue("$date", date);
            command.Parameters.AddWithValue("$id", UserId);
            command.ExecuteNonQuery();
        }
        public static List<(int id, int Value, string Category, DateTime Date)> LoadOperations(int UserId)
        {
            using var myCon = new SqliteConnection(Globaldata.connect);
            myCon.Open();
            var command = myCon.CreateCommand();

            string query = "SELECT Id, Value, Category, Date FROM `Operations` WHERE `User_id` = $id";
            command.CommandText = query;

            command.Parameters.AddWithValue("$id", UserId);
            List<(int id, int Value, string Category, DateTime Date)> ans = new();
            using (var reader = command.ExecuteReader())
            {
                if (reader.HasRows)
                {
                    while (reader.Read())
                    {
                        ans.Add(((int)reader[0], (int)reader[1], (string)reader[2], (DateTime)reader[3]));
                    }
                }
            }
            return ans;
        }

    }
}
