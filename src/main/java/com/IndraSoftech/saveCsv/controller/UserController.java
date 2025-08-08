package com.IndraSoftech.saveCsv.controller;

import com.IndraSoftech.saveCsv.entities.User;
import com.IndraSoftech.saveCsv.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/csv")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file.");
        }
        userService.save(file);
        return ResponseEntity.ok("CSV data uploaded successfully.");
    }

    @GetMapping("/allUser")
    public ResponseEntity<?> getUserDetails()
    {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            throw new RuntimeException("No users found");
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id)
    {
        User user = userService.getUserById(id); // throws 404 automatically if not found
        return ResponseEntity.ok(user);
    }
}
