package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
    @AllArgsConstructor
    public class Transaction {
        private int id;
        private int userId;
        private double amount;
        private String type; // Can be "DEPOSIT" or "WITHDRAW"

    }