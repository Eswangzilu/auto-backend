package com.jnzydzx.autobackend.controller;

import com.jnzydzx.autobackend.entity.User;
import com.jnzydzx.autobackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/management/reg")
@CrossOrigin("http://localhost:3000")
public class Management {
    private final UserService userService;

    public Management(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> index() {
        return userService.acquireUsers();
    }

    @PostMapping("/add")
    public List<User> register(@RequestBody List<User> users) {
        List<User> result = new ArrayList<>();
        for (User user: users) {
            result.add(
                    userService.addNewUser(user)
            );
        }
        return result;
    }

    @PostMapping("/exchange")
    public long update(@RequestBody List<User> users) {
        List<Integer> result = new ArrayList<>();
        for (User user: users) {
            result.add(
                userService.updateUserByAccountName(
                        user.getAccountName(),
                        user.getAccountPwd()
                )
            );
        }
        return result.stream().filter(numb -> numb != 0).count();
    }

    @PostMapping("/delete")
    public List<User> delete(@RequestBody List<User> users) {
        for (User user: users) {
            userService.deleteUserByAccountName(user.getAccountName());
        }
        return userService.acquireUsers();
    }
}
