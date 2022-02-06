using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GetUser : ControllerBase
    {
        // GET: api/<ValuesController>
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/<ValuesController>/5
        [HttpGet("{id:int}")]
        public string Get(int id)
        {
            Console.WriteLine("Запрос пользователя с id: " + id.ToString());
            return "Запрос пользователя с id: " +id.ToString();
        }

        // POST api/<ValuesController>
        [HttpPost]
        public void Post([FromBody] string value, int id, string name)
        {
            Console.WriteLine(value);
        }

        
    }
}
