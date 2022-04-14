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
    public class OperationsController : Controller
    {
        [Route("/AddOperation")]       
        [HttpGet]
        public IActionResult AddOperation(int value, string category, DateTime date, int id)
        {
            int ans = Database.Operations.AddOperation(value, category, date, id);
            if (ans != -1) return Ok(ans);
            else return BadRequest();
        }
        [Route("/DeleteOperation")]
        [HttpGet]
        public IActionResult AddOperation(int id)
        {
            if (Database.Operations.DeleteOperation(id)) return Ok(true);
            else return BadRequest();
        }
        [Route("/GetOperations")]
        [HttpGet]
        public string GetOperations(int UserId)
        {
            List<(int id, int Value, string Category, DateTime date)> ans = Database.Operations.LoadOperations(UserId);
            List<Operation> toret = new List<Operation>();
            foreach((int id, int Value, string Category, DateTime date) a in ans)
            {
                Operation tmp = new Operation();
                tmp.Id = a.id;
                tmp.Value = a.Value;
                tmp.Category = a.Category;
                tmp.date = a.date;
                toret.Add(tmp);
            }
            return JsonConvert.SerializeObject(toret);
        }
    }
}
