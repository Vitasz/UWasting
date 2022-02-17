using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Database
{
    static class Globaldata
    {
        public static string connect = "Data Source = "+Environment.GetEnvironmentVariable("DATABASE_URL");
    }
}
