using System;
using System.Collections.Generic;
using System.Linq;
using System.Data;
using Npgsql;
using System.Text;
using System.Threading.Tasks;

namespace Database
{

    public static class Registration
    {
        public static bool CheckingUser(string login)
        {
            using var myCon = new NpgsqlConnection(Globaldata.connect);
            myCon.Open();
            var cmd = new NpgsqlCommand("SELECT \"Login\" FROM \"Users\" WHERE \"Login\" = @log", myCon)
            {
                Parameters =
                {
                     new("@log", login),
                }
            };

            return cmd.ExecuteReader().HasRows;
        }
        public static bool Registrate(string login, string password, string name, string surname, int age)
        {
            using var myCon = new NpgsqlConnection(Globaldata.connect);
            myCon.Open();
            if (CheckingUser(login))
              return false;
            var cmd = new NpgsqlCommand("INSERT INTO \"Users\" (\"Login\", \"Password\", \"Name\", \"Surname\") VALUES (@log, @pass, @name, @surname)", myCon)
            {
                Parameters =
                {
                    new("@log", login),
                    new("@pass", password),
                    new("@name", name),
                    new("@surname", surname)
                }
            };
            cmd.ExecuteNonQuery();
            return true;
        }
        public static bool ChangeNameSurname(int id, string name, string surname)
        {
            using var myCon = new NpgsqlConnection(Globaldata.connect);
            myCon.Open();

            var cmd = new NpgsqlCommand("UPDATE \"Users\" SET \"Name\" = @name, \"Surname\" = @surname WHERE \"id\" = @id", myCon)
            {
                Parameters =
                {
                    new("@name", name),
                    new("@surname", surname),
                    new("@id", id)
                }
            };
            cmd.ExecuteNonQuery();
            return true;
        }
        public static bool ChangeLogin(int id, string login)
        {
            using var myCon = new NpgsqlConnection(Globaldata.connect);
            myCon.Open();

            var cmd = new NpgsqlCommand("UPDATE \"Users\" SET \"Login\" = @login WHERE \"id\" = @id", myCon)
            {
                Parameters =
                {
                    new("@login", login),
                    new("@id", id)
                }
            };
            cmd.ExecuteNonQuery();
            return true;
        }
        public static bool ChangePassword(int id, string password)
        {
            using var myCon = new NpgsqlConnection(Globaldata.connect);
            myCon.Open();

            var cmd = new NpgsqlCommand("UPDATE \"Users\" SET \"Password\" = @password WHERE \"id\" = @id", myCon)
            {
                Parameters = {
                    new("@password", password),
                    new("@id", id)
                }
            };

            cmd.ExecuteNonQuery();
            return true;
        }
    }
}