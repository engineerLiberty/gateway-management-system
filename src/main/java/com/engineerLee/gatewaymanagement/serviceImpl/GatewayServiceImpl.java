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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
            if (gateWayRequest.getSerialNumber() == null ||
                    gateWayRequest.getName() == null ||
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
            gateway.setIpAddress(gateway.getIpAddress());
            gateway.setSerialNumber(UUID.randomUUID().toString());
            gateway.setPeripheralDevices(peripheralDevices);

          gatewayRepo.save(gateway);
            return new ApiResponse<>("Success", "Gateway saved successfully",gateway);
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse<>("Failed", "Serial number must be unique", null);
        } catch (Exception e) {
            return new ApiResponse<>("Failed", "Failed to save gateway", null);
        }
    }

    @Override
    public void deleteGateway(String serialNumber) {
     gatewayRepo.deleteBySerialNumber(serialNumber);
    }
    @Override
    public ApiResponse<?> addPeripheralDevice(AddPeripheralRequest request) {
        Gateway gateway = gatewayRepo.findBySerialNumber(request.getSerialNumber());
        PeripheralDevice peripheralDevice;
        if (gateway != null){
            peripheralDevice = PeripheralDevice.builder()
                    .dateCreated(LocalDate.now())
                    .vendor(request.getPeripheralDto().getVendor())
                    .online(true)
                    .gateway(gateway)
                    .build();
        }
        else {
            throw new ValidationException("Invalid serial number: "+request.getSerialNumber());
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
