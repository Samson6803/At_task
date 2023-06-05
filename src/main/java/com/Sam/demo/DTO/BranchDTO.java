package com.Sam.demo.DTO;

public class BranchDTO {
    String name;
    String sha;

    public BranchDTO(String name, String sha) {
        this.name = name;
        this.sha = sha;
    }

    public String getName() {
        return name;
    }

    public String getSha() {
        return sha;
    }
}
