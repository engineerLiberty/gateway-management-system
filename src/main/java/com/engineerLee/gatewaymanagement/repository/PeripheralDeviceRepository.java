package com.engineerLee.gatewaymanagement.repository;

import com.engineerLee.gatewaymanagement.model.PeripheralDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeripheralDeviceRepository extends JpaRepository<PeripheralDevice, Long> {
        List<PeripheralDevice> findByGatewaySerialNumber(String serialNumber);
}
