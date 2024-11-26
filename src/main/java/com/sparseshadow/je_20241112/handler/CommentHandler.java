package com.sparseshadow.je_20241112.handler;

import com.sparseshadow.je_20241112.model.Article;
import com.sparseshadow.je_20241112.model.Comment;
import com.sparseshadow.je_20241112.model.User;
import com.sparseshadow.je_20241112.repository.ArticleRepository;
import com.sparseshadow.je_20241112.repository.CommentRepository;
import com.sparseshadow.je_20241112.repository.UserRepository;
import com.sparseshadow.je_20241112.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.HashMap;
import java.util.Map;

@Path("/comments")  
public class CommentHandler {

    @Inject  
    private CommentRepository commentRepository;

    @Inject  
    private UserRepository userRepository;

    @Inject  
    private ArticleRepository articleRepository;

    @POST  
    @Path("/")  
    @Secured({"user", "admin"})  
    @Consumes(MediaType.APPLICATION_JSON)  
    @Produces(MediaType.APPLICATION_JSON)  
    public Response createComment(Comment comment , @Context SecurityContext securityContext ) {
        User user = userRepository.findByID(Integer.valueOf(securityContext.getUserPrincipal().getName()));  
        comment.setUser(user);  
        Article article = articleRepository.findByID(comment.getArticleId());  
        comment.setArticle(article);  
        comment.setCreatedAt(System.currentTimeMillis() / 1000);  
        commentRepository.create(comment);  
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        return Response.status(Response.Status.OK).entity(res).build();
    }
}