package com.engineerLee.gatewaymanagement.repository;

import com.engineerLee.gatewaymanagement.model.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {
    void deleteBySerialNumber(String serialNumber);

    Gateway findBySerialNumber(String serialNumber);
}
