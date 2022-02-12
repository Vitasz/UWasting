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
    public class UserController : Controller
    {
        [Route("/RegistrateUser")]
        [HttpPost]
        public IActionResult RegistrateUser(string login, string password, string name, string surname, int age)
        {
            if (Database.Registration.Registrate(login, password, name, surname, age))
            {
                return Ok(Database.Autorization.Join(login, password));
            }
            else return BadRequest();
        }

        [Route("/GetByLoginAndPassword")]
        [HttpGet]
        public IActionResult GetByLoginAndPassword(string login, string password)
        {
            (int id, string email, string name, string surname) tmp = Database.Autorization.Join(login, password);
            //Console.WriteLine("Не стучи, заебал");
            Console.WriteLine(tmp);
            if (tmp.Item1 == -1) return BadRequest();
            User user = new User();
            user.Name = tmp.name;
            user.Surname = tmp.surname;
            user.id = tmp.id;
            user.email = tmp.email;
            return Ok(user);
           
        }
    }
}
