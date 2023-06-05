package com.Sam.demo.Repositories;

import com.Sam.demo.DTO.BranchDTO;
import com.Sam.demo.DTO.RepositoryDTO;
import com.Sam.demo.Exceptions.Exceptions.ApiParsingException;
import com.Sam.demo.Exceptions.Exceptions.ApiUserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class GitRepoRepository {
    public List<RepositoryDTO> getRepositories(String username){
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.github.com/users/%s/repos".formatted(username);
        ResponseEntity<String> resultEntity;
        try{
            resultEntity = restTemplate.getForEntity(url, String.class);
        }catch (Exception e){
            throw new ApiUserNotFoundException("User not found");
        }

        List<RepositoryDTO> repositories = new ArrayList<>();
        try{
            String result = resultEntity.getBody();
            JsonNode data = objectMapper.readTree(result);
            Iterator<JsonNode> elements = data.elements();
            while (elements.hasNext()){
                JsonNode element = elements.next();
                String isFork = element.get("fork").asText();
                if(isFork.equals("true")) continue;

                String name = element.get("name").asText();
                JsonNode owner = element.get("owner");
                String login = owner.get("login").asText();
                List<BranchDTO> branches = getBranches(username, name);
                repositories.add(new RepositoryDTO(name, login, branches));
            }
            return repositories;
        }catch (Exception e){
            throw new ApiParsingException("Server problem during parsing data");
        }
    }

    private List<BranchDTO> getBranches(String username, String branchName) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.github.com/repos/%s/%s/branches".formatted(username,branchName);
        ResponseEntity<String> resultEntity = restTemplate.getForEntity(url, String.class);

        List<BranchDTO> branches = new ArrayList<>();
        String result = resultEntity.getBody();
        JsonNode data = objectMapper.readTree(result);
        Iterator<JsonNode> elements = data.elements();
        while (elements.hasNext()){
            JsonNode element = elements.next();
            String name = element.get("name").asText();
            JsonNode commit = element.get("commit");
            String sha = commit.get("sha").asText();
            branches.add(new BranchDTO(name, sha));
        }
        return branches;
    }
}
