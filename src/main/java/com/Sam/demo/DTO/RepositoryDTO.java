package com.Sam.demo.DTO;

import java.util.List;

public class RepositoryDTO {
    String name;
    String ownerLogin;
    List<BranchDTO> branches;

    public RepositoryDTO(String name, String ownerLogin, List<BranchDTO> branches) {
        this.name = name;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getName() {
        return name;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<BranchDTO> getBranches() {
        return branches;
    }
}
