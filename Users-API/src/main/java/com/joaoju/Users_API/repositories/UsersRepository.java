package com.joaoju.Users_API.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.joaoju.Users_API.models.UsersModel;

@Repository
public interface UsersRepository extends JpaRepository<UsersModel, UUID>{
    List<UsersModel> findByNomeContainingIgnoreCase(String nome);


}
