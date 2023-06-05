package com.Sam.demo.Controllers;

import com.Sam.demo.DTO.RepositoryDTO;
import com.Sam.demo.Exceptions.Exceptions.ApiUnhandledFormatException;
import com.Sam.demo.Services.GitRepoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GitRepoController {
    private final GitRepoService gitRepoService;
    public GitRepoController(GitRepoService gitRepoService){
        this.gitRepoService = gitRepoService;
    }

    @GetMapping(value = "/{username}")
    public List<RepositoryDTO> getUserRepositories(@PathVariable String username, @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader){
        if(acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)){
            throw new ApiUnhandledFormatException("XML is not handled, incorrect 'Accept' header");
        }
        return gitRepoService.getReposInfo(username);
    }

}
