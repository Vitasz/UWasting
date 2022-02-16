using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server
{
    [Serializable]
    public class User
    {
        public string name { get; set; }
        public string surname { get; set; }
        public string email { get; set; }
        public int id { get; set; }
    }
}
