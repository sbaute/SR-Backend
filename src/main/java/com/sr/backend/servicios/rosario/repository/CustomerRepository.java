package com.serviciosRosario.ServiciosRosario.repository;

import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.emails")
    List<Customer> findAllWithEmails();

    boolean existsByDocNumber(String document);

    @Query("SELECT MAX(CAST(c.code AS int)) FROM Customer c WHERE c.temporary = false")
    Integer findMaxPermanentCustomerCode();
}
