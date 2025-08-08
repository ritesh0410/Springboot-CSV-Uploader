package com.IndraSoftech.saveCsv.service;

import com.IndraSoftech.saveCsv.entities.User;
import com.IndraSoftech.saveCsv.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<User> users = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                User user = new User();
                user.setName(record.get("name"));
                user.setEmail(record.get("email"));
                user.setAge(Integer.parseInt(record.get("age")));
                users.add(user);
            }
            userRepository.saveAll(users);

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }




    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
