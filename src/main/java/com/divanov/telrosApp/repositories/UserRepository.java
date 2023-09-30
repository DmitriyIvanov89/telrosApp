package com.divanov.telrosApp.repositories;

import com.divanov.telrosApp.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {
    @Query("select u from UserApp u " +
            "where lower(u.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(u.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<UserApp> search(@Param("searchTerm") String searchTerm);
}
