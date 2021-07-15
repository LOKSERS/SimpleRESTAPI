package com.example.demo.services;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Exceptions.PostNotFoundException;
import com.example.demo.repository.CommentRepository;
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
public class CommentService {

    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    public final CommentRepository commentRepository;
    public final PostRepository postRepository;
    public final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findPostById(postId).orElseThrow(()->new PostNotFoundException("Post not found!"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Saving Comment for Post: " + post.getId());

        return commentRepository.save(comment);
      }

      public List<Comment> getAllCommentForPost(Long postId){
       Post post = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException("Post not found!"));
       List<Comment> comments = commentRepository.findAllByPost(post);
       return comments;
      }

      public void deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
      }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }


}
