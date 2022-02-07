using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class User : Controller
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
        public string GetByLoginAndPassword(string login, string password)
        {
            int id = Database.Autorization.Join(login, password);
            return "Запрос пользователя с id: " + id.ToString();
        }
    }
}
