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
import java.util.List;
import java.util.Map;

@Path("/articles")  
public class ArticleHandler {

    @Inject  
    private ArticleRepository articleRepository;

    @Inject  
    private CommentRepository commentRepository;

    @Inject  
    private UserRepository userRepository;

    @GET  
    @Path("/")  
    @Produces(MediaType.APPLICATION_JSON)  
    public Response getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        Map<String, Object> res= new HashMap<>();
        res.put("code", Response.Status.OK);
        res.put("data", articles);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @GET  
    @Path("/{id}")  
    @Produces(MediaType.APPLICATION_JSON)  
    public Response getArticleById(@PathParam("id") Integer id) {
        Article article = articleRepository.findByID(id);
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        res.put("data", article);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @GET  
    @Path("/{id}/comments")  
    @Produces(MediaType.APPLICATION_JSON)  
    public Response getComments(@PathParam("id") Integer id) {
        List<Comment> comments = commentRepository.findByArticleId(id);
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        res.put("data", comments);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @POST  
    @Path("/")  
    @Secured({"admin"})  
    @Consumes(MediaType.APPLICATION_JSON)  
    @Produces(MediaType.APPLICATION_JSON)  
    public Response createArticle(Article article , @Context SecurityContext securityContext ) {
        User author = userRepository.findByID(Integer.valueOf(securityContext.getUserPrincipal().getName()));  
        article.setAuthor(author);  
        article.setCreatedAt(System.currentTimeMillis() / 1000);  
        articleRepository.create(article);  
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @PUT  
    @Path("/")  
    @Secured({"admin"})  
    @Consumes(MediaType.APPLICATION_JSON)  
    @Produces(MediaType.APPLICATION_JSON)  
    public Response updateArticle(Article article , @Context SecurityContext securityContext ) {
        article.setAuthorId(Integer.valueOf(securityContext.getUserPrincipal().getName()));  
        articleRepository.update(article);  
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        return Response.status(Response.Status.OK).entity(res).build();
    }
}
