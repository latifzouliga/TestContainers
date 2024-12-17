package com.zouliga.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    // this is a throw away container.
    // This container is not affecting the database in the dev env because this is a separate container
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    PostRepository postRepository;

    // Before Spring 3.1
    /*
    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry){
        String databaseUrl = "jdbc:postgresql://localhost:5432/posts";

        registry.add("spring.datasource.url", () -> databaseUrl);
        registry.add("spring.datasource.username", () -> "zouliga");
        registry.add("spring.datasource.password", () -> "secret");
    }
     */

    @DisplayName("Test Connection Established")
    @Test
    void connectionEstablished(){
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();

    }


    @BeforeEach
    void setUp() {
//        List<Post> posts = List.of(new Post(1, 1, "hello World", "this is for learning test containers", null));
//        postRepository.saveAll(posts);

        Post post = new Post(1,1,"Hello", "World", null);
        postRepository.save(post);
    }

    @DisplayName("Get Post By Title")
    @Test
    void getPostByTitle(){
        Post post = postRepository.findByTitle("Hello");
        assertThat(post).isNotNull();
        assertThat(post.body()).isEqualTo("World");
    }
}


















