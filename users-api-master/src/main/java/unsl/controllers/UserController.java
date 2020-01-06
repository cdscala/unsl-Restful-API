package unsl.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unsl.entities.ResponseError;
import unsl.entities.User;
import unsl.entities.User.Status;
import unsl.services.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/clientes")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
       return userService.getAll();
    }

    @GetMapping(value = "/clientes/{clienteId}")
    @ResponseBody
    public Object getUser(@PathVariable("clienteId") Long userId) {
        User user = userService.getUser(userId);
        if ( user == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Cliente %d no encontrado", userId)), HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @GetMapping(value = "/clientes/buscar")
    @ResponseBody
    public Object searchUser(@RequestParam("dni") Long dni) {
        User user = userService.findByDni(dni);
        if ( user == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Cliente dni: %d no encontrado", dni)), HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @PostMapping(value = "/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Object createUser(@RequestBody User user) {
        user.setEstado(Status.ACTIVO);
        return userService.saveUser(user);
    }

    @PutMapping(value = "/clientes")
    @ResponseBody
    public Object updateUser(@RequestBody User User) {
        User res = userService.updateUser(User);
        if ( res == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Usuario ID: %d no encontrado", User.getId())), HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @DeleteMapping(value = "/clientes/{id}")
    @ResponseBody
    public Object deleteUser(@PathVariable Long id) {
        User res = userService.deleteUser(id);
        if ( res == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Usuario ID: %d no encontrado", id)), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

}

