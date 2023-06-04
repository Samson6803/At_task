package com.Sam.demo.Controller;

import com.Sam.demo.DTO.ResponseDTO;
import com.Sam.demo.Service.GitRepoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitRepoController {
    private final GitRepoService gitRepoService;
    public GitRepoController(GitRepoService gitRepoService){
        this.gitRepoService = gitRepoService;
    }

    @GetMapping(value = "/{username}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> getUserRepositories(@PathVariable String username, @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader){
        if(acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)){
            ResponseDTO responseDTO = new ResponseDTO(HttpStatus.NOT_ACCEPTABLE.value(),
                    "XML response is not supported format");
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
        return null;
    }

}
