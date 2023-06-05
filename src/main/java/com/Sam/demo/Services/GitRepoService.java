package com.Sam.demo.Services;

import com.Sam.demo.DTO.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

@Service
public class GitRepoService {
    public ResponseEntity<Object> getReposInfo(String username) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.github.com/users/%s/repos?fork=false".formatted(username);
        ResponseEntity<String> resultEntity;
        try{
            resultEntity = restTemplate.getForEntity(url, String.class);
        }catch (Exception e){
            ResponseDTO responseDTO = new ResponseDTO(404,
                    "User not found");
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }

        ArrayNode jsonNode = objectMapper.createArrayNode();
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
                ObjectNode repository = objectMapper.createObjectNode();
                repository.put("name", name);
                repository.put("login", login);
                ArrayNode branches = getBranches(username, name);
                repository.set("branches", branches);
                jsonNode.add(repository);
            }
            return ResponseEntity.ok(jsonNode.toString());
        }catch (Exception e){
            ResponseDTO responseDTO = new ResponseDTO(500,
                    "Server problem during parsing data");
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ArrayNode getBranches(String username, String branchName) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://api.github.com/repos/%s/%s/branches".formatted(username,branchName);
        ResponseEntity<String> resultEntity = restTemplate.getForEntity(url, String.class);

        ArrayNode jsonNodes = objectMapper.createArrayNode();
        String result = resultEntity.getBody();
        JsonNode data = objectMapper.readTree(result);
        Iterator<JsonNode> elements = data.elements();
        while (elements.hasNext()){
            JsonNode element = elements.next();
            String name = element.get("name").asText();
            JsonNode commit = element.get("commit");
            String sha = commit.get("sha").asText();
            ObjectNode branch = objectMapper.createObjectNode();
            branch.put("name", name);
            branch.put("sha", sha);
            jsonNodes.add(branch);
        }
        return jsonNodes;
    }
}
