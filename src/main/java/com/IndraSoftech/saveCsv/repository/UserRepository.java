package com.IndraSoftech.saveCsv.repository;

import com.IndraSoftech.saveCsv.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
