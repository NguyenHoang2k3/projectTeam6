package org.example.springmvc.repository;

import org.example.springmvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        UserCustomRepository {
    // Query method
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // JPQL = HQL
    @Query("select u from User u " +
            " where u.username like :searchText" +
            " or u.address like :searchText " +
            " or u.email like :searchText " +
            " or u.phone like :searchText")
    List<User> findByAllField(String searchText);
}
