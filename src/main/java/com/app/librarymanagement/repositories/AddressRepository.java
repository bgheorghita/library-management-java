package com.app.librarymanagement.repositories;

import com.app.librarymanagement.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
