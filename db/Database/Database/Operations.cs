﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using System.Text;
using System.Threading.Tasks;
using Npgsql;

namespace Database
{
    public static class Operations
    {
        public static void SaveOperation(int value, string category, DateTime date, int UserId)
        {
            using var myCon = new NpgsqlConnection(Globaldata.connect);
            myCon.Open();

            var cmd = new NpgsqlCommand("INSERT INTO \"Operations\" (\"Value\", \"Category\", \"Date\", \"User_id\") VALUES (@sum, @cat, @date, @id)", myCon)
            {
                Parameters =
                {
                    new("@sum", value),
                    new("@cat", category),
                    new("@date", date),
                    new("@id", UserId)
                }
            };

            cmd.ExecuteNonQuery();
        }
        public static List<(int id, int Value, string Category, DateTime Date)> LoadOperations(int UserId)
        {
            using var myCon = new NpgsqlConnection(Globaldata.connect);
            myCon.Open();

            var cmd = new NpgsqlCommand("SELECT \"id\", \"Value\", \"Category\", \"Date\" FROM \"Operations\" WHERE \"User_id\" = @id", myCon)
            {
                Parameters =
                {
                    new("@id", UserId)
                }
            };

            List<(int id, int Value, string Category, DateTime Date)> ans = new();
            using (var reader = cmd.ExecuteReader())
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
