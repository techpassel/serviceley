package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.Address;
import com.tp.serviceley.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("from Address a where a.user=user and a.isDefaultAddress=true")
    Address findUserDefaultAddress(User user);

    List<Address> findByUser(User user);
}
