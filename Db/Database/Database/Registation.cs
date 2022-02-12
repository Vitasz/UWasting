﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using System.Data.OleDb;
using System.Text;
using System.Threading.Tasks;

namespace Database
{

    public static class Registration
    {
        public static bool CheckingUser(string login)
        {
            using var myCon = new OleDbConnection(Globaldata.connect);

            myCon.Open();

            string query = "SELECT Login FROM `Users` WHERE `Login` = @log";
            DataTable table1 = new();
            OleDbDataAdapter adapt = new();
            OleDbCommand com = new(query, myCon);
            com.Parameters.AddWithValue("@log", login);
            adapt.SelectCommand = com;
            adapt.Fill(table1);

            return table1.Rows.Count > 0;
        }
        public static bool Registrate(string login, string password, string name, string surname, int age)
        {
            using var myCon = new OleDbConnection(Globaldata.connect);
            if (CheckingUser(login))
                return false;
            myCon.Open();
            string query = "INSERT INTO `Users` (`Login`, `Password`, `Name`, `Surname`, `Age`) VALUES (@log, @pass, @name, @surname, @age)";
            OleDbCommand com = new(query, myCon);
            com.Parameters.AddWithValue("@log", login);
            com.Parameters.AddWithValue("@pass", password);
            com.Parameters.AddWithValue("@name", name);
            com.Parameters.AddWithValue("@surname", surname);
            com.Parameters.AddWithValue("@age", age.ToString());
            com.ExecuteNonQuery();
            return true;
        }
    }
}