package com.crudrest.repository;

import com.crudrest.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select client from Client client where client.name = ?1")
    Client findByName(String name);
}
