package com.example.crudrapido.repository;
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.example.crudrapido.entity.AppUser;
 
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
 
    Optional<AppUser> findByEmail(String email);
}