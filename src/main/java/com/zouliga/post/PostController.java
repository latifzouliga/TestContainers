package com.zouliga.post;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Integer id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post save(@RequestBody @Valid Post post) {
        return postRepository.save(post);
    }


    @PutMapping("/{id}")
    public Post update(@PathVariable Integer id, @RequestBody Post post) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isPresent()) {
            Post updatedPost = new Post(
                    existingPost.get().id(),
                    existingPost.get().userId(),
                    post.title(),
                    post.body(),
                    existingPost.get().version()
            );
            return postRepository.save(updatedPost);
        }

        throw new PostNotFoundException();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()){
            postRepository.deleteById(id);
        }
        throw new PostNotFoundException();
    }
}
