package com.example.demo.facade;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.Entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentFacade {
    public CommentDTO commentToCommentDTO(Comment comment){
         CommentDTO commentDTO = new CommentDTO();
         commentDTO.setId(comment.getId());
         commentDTO.setMessage(comment.getMessage());
         commentDTO.setUsername(comment.getUsername());

         return commentDTO;
    }
}
