using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Newtonsoft.Json;
namespace Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class Operations : Controller
    {
        [Route("/AddOperation")]       
        [HttpPost]
        public void AddOperation(int value, string category, DateTime date, int id)
        {
            Database.Operations.SaveOperation(value, category, date, id);
        }

        [Route("/GetOperations")]
        [HttpGet]
        public string GetOperations(int UserId)
        {
            var ans = Database.Operations.LoadOperations(UserId);
            Console.WriteLine(ans.Count);
            foreach ((int, string, DateTime) a in ans) Console.WriteLine(a.Item1.ToString()+ a.Item2+ a.Item3.ToString());
            return JsonConvert.SerializeObject(ans);
        }
    }
}
