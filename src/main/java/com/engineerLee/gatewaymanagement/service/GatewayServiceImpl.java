package com.engineerLee.gatewaymanagement.service;

import com.engineerLee.gatewaymanagement.dto.ApiResponse;
import com.engineerLee.gatewaymanagement.dto.GateWayRequest;
import com.engineerLee.gatewaymanagement.exception.DataIntegrityViolationException;
import com.engineerLee.gatewaymanagement.model.Gateway;
import com.engineerLee.gatewaymanagement.model.PeripheralDevice;
import com.engineerLee.gatewaymanagement.repository.GatewayRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService{
    private final GatewayRepository gatewayRepo;

    @Override
    public List<ApiResponse<Gateway>> getAllGateways() {
        return null;
    }

    @Override
    public ApiResponse<Gateway> getGatewayBySerialNumber(String serialNumber) {
        return null;
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

    }

    @Override
    public void addPeripheralDevice(String serialNumber, PeripheralDevice device) {

    }

    @Override
    public void removePeripheralDevice(String serialNumber, Long deviceId) {

    }
}
