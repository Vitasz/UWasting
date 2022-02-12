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
        public string RegistrateUser(string login, string password, string name, string surname, int age)
        {
            if (Database.Registration.Registrate(login, password, name, surname, age))
            {
                return "Регистрация успешна";
            }
            else return "Регистрация завершилась с ошибкой";
        }

        [Route("/GetByLoginAndPassword")]
        [HttpGet]
        public IActionResult GetByLoginAndPassword(string login, string password)
        {
            (int id, string name, string surname, string email) tmp = Database.Autorization.Join(login, password);
            //Console.WriteLine("Не стучи, заебал");
            Console.WriteLine(tmp);
            if (tmp.id == -1) return BadRequest();
            User user = new User();
            user.name = tmp.name;
            user.surname = tmp.surname;
            user.email = tmp.email;
            user.id = tmp.id;
            return Ok(user);
        }
    }
}
