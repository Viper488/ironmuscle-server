package com.muscle.user;

import com.muscle.user.dto.IronUserDto;
import com.muscle.user.dto.RoleDto;
import com.muscle.user.service.UserService;
import com.muscle.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/user")
    public UserDetails getUser(@RequestParam("email") String email){
        return userService.loadUserByUsername(email);
    }

    @PostMapping("/user/save")
    public IronUserDto saveUser(@RequestBody IronUserDto ironUserDto){
        return userService.saveUser(ironUserDto);
    }

    @PostMapping("/user/add-role")
    public void addRoleToUser(@RequestParam("username") String username, @RequestParam("roleName") String roleName){
        userService.addRoleToUser(username, roleName);
    }

    @GetMapping("/users")
    public List<IronUserDto> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/role/save")
    public RoleDto saveRole(@RequestBody RoleDto roleDto){
        return userService.saveRole(roleDto);
    }
}
