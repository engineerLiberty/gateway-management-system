package com.engineerLee.gatewaymanagement.serviceImpl;

import com.engineerLee.gatewaymanagement.dto.AddPeripheralRequest;
import com.engineerLee.gatewaymanagement.dto.ApiResponse;
import com.engineerLee.gatewaymanagement.dto.GateWayRequest;
import com.engineerLee.gatewaymanagement.exception.DataIntegrityViolationException;
import com.engineerLee.gatewaymanagement.exception.ValidationException;
import com.engineerLee.gatewaymanagement.model.Gateway;
import com.engineerLee.gatewaymanagement.model.PeripheralDevice;
import com.engineerLee.gatewaymanagement.repository.GatewayRepository;
import com.engineerLee.gatewaymanagement.repository.PeripheralDeviceRepository;
import com.engineerLee.gatewaymanagement.service.GatewayService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class GatewayServiceImpl implements GatewayService {
    private final GatewayRepository gatewayRepo;
    private final PeripheralDeviceRepository peripheralDeviceRepo;

    @Override
    public ApiResponse<?> getAllGateways() {
        return ApiResponse.builder()
                .status("SUCCESS")
                .message("Data retrieved successfully")
                .data(gatewayRepo.findAll())
                .build();
    }

    @Override
    public ApiResponse<?> getGatewayBySerialNumber(String serialNumber) {
        return ApiResponse.builder()
                .status("SUCCESS")
                .message("Data retrieved successfully")
                .data(gatewayRepo.findBySerialNumber(serialNumber))
                .build();
    }

    @Override
    public ApiResponse<Gateway> saveGateway(GateWayRequest gateWayRequest) {
        try {
            if (gateWayRequest.getName() == null ||
                    gateWayRequest.getIpAddress() == null)
            {
                return new ApiResponse<>("Failed", "Gateway fields cannot be null", null);
            }

            List<PeripheralDevice> peripheralDevices = new ArrayList<>();
            PeripheralDevice peripheralDevice = new PeripheralDevice();
            peripheralDevice.setDateCreated(LocalDate.now());
            peripheralDevices.add(peripheralDevice);
            Gateway gateway = new Gateway();
            gateway.setName(gateWayRequest.getName());
            gateway.setIpAddress(gateWayRequest.getIpAddress());
            gateway.setSerialNumber(UUID.randomUUID().toString());
//            gateway.setPeripheralDevices(peripheralDevices);
            gatewayRepo.save(gateway);
            return new ApiResponse<>("Success", "Gateway saved successfully",gateway);
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse<>("Failed", "Serial number must be unique", null);
        } catch (Exception e) {
            return new ApiResponse<>("Failed", "Failed to save gateway", null);
        }
    }

    @Override
    @Transactional
    public void deleteGateway(String serialNumber) {
        log.info("Deleting gateway with serialNumber: {}", serialNumber);
        Gateway gateway = gatewayRepo.findBySerialNumber(serialNumber);
        if (gateway != null) {
            gatewayRepo.delete(gateway);
            log.info("Gateway deleted successfully: {}", gateway);
        } else {
            log.warn("Gateway not found with serialNumber: {}", serialNumber);
        }
    }

    @Override
    public ApiResponse<?> addPeripheralDevice(AddPeripheralRequest request) {
        log.info("addPeripheralDevice method invoked for serialNumber: {}", request.getSerialNumber());

        Gateway gateway = gatewayRepo.findBySerialNumber(request.getSerialNumber());
        PeripheralDevice  peripheralDevice = new PeripheralDevice();
        if (gateway != null) {
            log.info("Gateway found for serialNumber: {}", request.getSerialNumber());

            peripheralDevice.setVendor(request.getPeripheralDto().getVendor());
            peripheralDevice.setDateCreated(LocalDate.now());
            peripheralDevice.setOnline(true);
            peripheralDevice.setGateway(gateway);
            peripheralDeviceRepo.save(peripheralDevice);
            log.info("Peripheral device saved: {}", peripheralDevice);
        } else {
            throw new ValidationException("Invalid serial number: " + request.getSerialNumber());
        }

        return ApiResponse.builder()
                .status("SUCCESS")
                .message("Peripheral device added successfully")
                .data(peripheralDevice)
                .build();
    }

    @Override
    public void removePeripheralDevice(String serialNumber, Long deviceId) {
        Gateway gateway = gatewayRepo.findBySerialNumber(serialNumber);
        PeripheralDevice peripheralDevice = peripheralDeviceRepo.findByGateway(gateway);
        if (peripheralDevice != null && Objects.equals(peripheralDevice.getUid(), deviceId)){
            peripheralDeviceRepo.deleteByUid(deviceId);
        }
    }
}
