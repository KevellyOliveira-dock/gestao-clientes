package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Checkpoint {
    private double saldo;
    private LocalDateTime dataDeCheck;
}