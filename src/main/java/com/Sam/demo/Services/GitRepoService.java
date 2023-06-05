package com.Sam.demo.Services;

import com.Sam.demo.DTO.RepositoryDTO;
import com.Sam.demo.DTO.ResponseDTO;
import com.Sam.demo.Repositories.GitRepoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

@Service
public class GitRepoService {
    private final GitRepoRepository repository;

    GitRepoService(GitRepoRepository repository){
        this.repository = repository;
    }
    public List<RepositoryDTO> getReposInfo(String username) {
        return repository.getRepositories(username);
    }


}
