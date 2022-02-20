package guru.springframework.spring5webapp.repository;

import guru.springframework.spring5webapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findAllByCustomerNameLike(String customerName);
}