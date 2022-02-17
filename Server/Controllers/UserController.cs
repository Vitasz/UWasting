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
        [HttpGet]
        public bool RegistrateUser(string login, string password, string name, string surname)
        {
            return Database.Registration.Registrate(login, password, name, surname, 0);
        }

        [Route("/GetByLoginAndPassword")]
        [HttpGet]
        public IActionResult GetByLoginAndPassword(string login, string password)
        {
            (int id, string email, string name, string surname) tmp = Database.Autorization.Join(login, password);
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
        [Route("/FindLoginInDB")]
        [HttpGet]
        public IActionResult FindLoginInDB(string login)
        {
            return Ok(Database.Registration.CheckingUser(login));
        }
        [Route("/СhangeNameSurname")]
        [HttpGet]
        public IActionResult СhangeNameSurname(int id, string name, string surname)
        {
            return Ok(Database.Registration.ChangeNameSurname(id, name, surname));
        }
        [Route("/ChangeLogin")]
        [HttpGet]
        public IActionResult ChangeLogin(int id, string login)
        {
            if (Database.Registration.CheckingUser(login)) return BadRequest();
            return Ok(Database.Registration.ChangeLogin(id, login));
        }
        [Route("/ChangePassword")]
        [HttpGet]
        public IActionResult ChangePassword(int id, string password)
        {
            return Ok(Database.Registration.ChangePassword(id, password));
        }
    }
}
