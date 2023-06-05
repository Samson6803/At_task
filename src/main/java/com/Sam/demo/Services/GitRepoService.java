package com.Sam.demo.Services;

import com.Sam.demo.DTO.RepositoryDTO;
import com.Sam.demo.Repositories.GitRepoRepository;
import org.springframework.stereotype.Service;

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
