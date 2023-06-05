package com.Sam.demo.DTO;

import java.util.List;

public record RepositoryDTO(String name, String login, List<BranchDTO> branches) {
}
