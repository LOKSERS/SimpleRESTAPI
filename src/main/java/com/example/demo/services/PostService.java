package com.example.demo.services;

import com.example.demo.DTO.PostDTO;
import com.example.demo.Entity.ImageModel;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Exceptions.PostNotFoundException;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(PostDTO postDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);
        LOG.info("Saving post for user: " + user.getUsername());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long id,Principal principal){
        User user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(id,user)
                .orElseThrow(()->new PostNotFoundException("Cannot find a post: " + user.getEmail()));
    }

    public List<Post> getAllPostForUser(Principal principal){
        User user = getUserByPrincipal(principal);
        return  postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Post likePost(Long postId, String username){
        Post post = postRepository.findPostById(postId)
                .orElseThrow(()->new PostNotFoundException("Cannot find a posts"));

        Optional<String> userLiked = post.getLikedUsers().stream().filter(u->u.equals(username)).findAny();

        if(userLiked.isPresent()){
            post.setLikes(post.getLikes()-1);
            post.getLikedUsers().remove(username);

        }else{
            post.setLikes(post.getLikes()+1);
            post.getLikedUsers().add(username);
        }
    return postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal){//спорно
        User user = getUserByPrincipal(principal);

        Post post = postRepository.findPostByIdAndUser(postId,user)
                .orElseThrow(()-> new PostNotFoundException("Post does not exist"));

        Optional<ImageModel> image = imageRepository.findByPostId(post.getId());

        image.ifPresent(imageRepository::delete);

        postRepository.delete(post);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }


}
