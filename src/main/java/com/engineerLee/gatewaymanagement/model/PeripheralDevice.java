package com.engineerLee.gatewaymanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeripheralDevice {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long uid;

        private String vendor;
        private LocalDate dateCreated;
        private boolean online;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "gateway_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JsonIgnore
        private Gateway gateway;
    }

