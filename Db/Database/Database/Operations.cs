using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using System.Data.OleDb;
using System.Text;
using System.Threading.Tasks;

namespace Database
{
    public static class Operations
    {
        public static void SaveOperation(int value, string category, DateTime date, int UserId)
        {
            using var myCon = new OleDbConnection(Globaldata.connect);
            DataTable table = new();
            OleDbDataAdapter adapt = new();
            myCon.Open();
            string query = "INSERT INTO `Operations` (`Value`, `Category`, `Date`, `User_id`) VALUES (@sum, @cat, @date, @id)";
            OleDbCommand com = new(query, myCon);
            com.Parameters.AddWithValue("@sum", value);
            com.Parameters.AddWithValue("@cat", category);
            com.Parameters.AddWithValue("@date", date);
            com.Parameters.AddWithValue("@id", UserId);
            adapt.SelectCommand = com;
            adapt.Fill(table);
        }
        public static List<(int id, int Value, string Category, DateTime Date)> LoadOperations(int UserId)
        {
            using var myCon = new OleDbConnection(Globaldata.connect);
            myCon.Open();
            string query = "SELECT Id, Value, Category, Date FROM `Operations` WHERE `User_id` = @id";
            OleDbCommand com = new(query, myCon);
            com.Parameters.AddWithValue("@id", UserId);
            OleDbDataReader reader = com.ExecuteReader();
            List<(int id, int Value, string Category, DateTime Date)> ans = new();
            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    ans.Add(((int)reader[0], (int)reader[1], (string)reader[2], (DateTime)reader[3]));
                }
            }
            reader.Close();
            return ans;
        }

    }
}
