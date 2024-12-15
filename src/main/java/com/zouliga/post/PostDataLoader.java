package com.zouliga.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PostDataLoader implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(PostDataLoader.class);
    private final static String POSTS_JSON = "/data/posts.json";
    private final ObjectMapper objectMapper;
    private final PostRepository postRepository;

    public PostDataLoader(ObjectMapper objectMapper, PostRepository postRepository) {
        this.objectMapper = objectMapper;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (postRepository.count() == 0){
            log.info("Loading posts into database from JSON: {}", POSTS_JSON);
            try (var inputStream = TypeReference.class.getResourceAsStream(POSTS_JSON)){
                Posts response = objectMapper.readValue(inputStream, Posts.class);
                postRepository.saveAll(response.posts());
            }catch (IOException e){
                throw  new RuntimeException("Failed to read JSON data", e);
            }
        }

    }
}
