﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.OleDb;
using System.Threading.Tasks;

namespace Database
{
    public class ClassWaste
    {
        private OleDbConnection myCon;
        public void savewaste()
        {
            myCon = new OleDbConnection(Globaldata.connect);
            DataTable table = new DataTable();
            OleDbDataAdapter adapt = new OleDbDataAdapter();
            myCon.Open();
            string query = "INSERT INTO `NonWaste` (`Summary`, `Category`, `Date`, `User_id`) VALUES (@sum, @cat, @date, @id)";
            OleDbCommand com = new OleDbCommand(query, myCon);
            com.Parameters.AddWithValue("@sum", /*IncomeTXT*/.Text);
            com.Parameters.AddWithValue("@cat", /*CatigotyTXT*/.Text);
            com.Parameters.AddWithValue("@date", /*DateTXT*/.Text);
            com.Parameters.AddWithValue("@id", Globaldata.userID);
            adapt.SelectCommand = com;
            adapt.Fill(table);
            myCon.Close();
        }

        public void loadwaste()
        {
            myCon = new OleDbConnection(Globaldata.connect);
            myCon.Open();
            string query = "SELECT Waste, Category, Date FROM `Waste` WHERE `User_id` = @id";
            OleDbCommand com = new OleDbCommand(query, myCon);
            com.Parameters.AddWithValue("@id", Globaldata.userID);
            OleDbDataReader reader = com.ExecuteReader();
            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    /**/IncomedbTXT.Items.Add(reader[0].ToString());
                    /**/CatTXT.Items.Add(reader[1].ToString());
                    /**/DatedbTXT.Items.Add(reader[2].ToString());
                }
            }
            reader.Close();
            myCon.Close();
        }
    }
}
