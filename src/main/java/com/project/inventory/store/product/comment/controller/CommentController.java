package com.project.inventory.store.product.comment.controller;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.store.order.model.Order;
import com.project.inventory.store.order.service.OrderService;
import com.project.inventory.store.product.comment.model.Comment;
import com.project.inventory.store.product.comment.service.CommentService;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/v1/comments" )
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private OrderService orderService;

    @RequestMapping( value = "", method = RequestMethod.POST )
    public ResponseEntity<?> saveComment(@RequestBody Comment comment) {
        Account account = authenticatedUser.getUserDetails();

        Product prod = productService.getProductById( comment.getProduct().getId() );
        comment.setAccountId( account.getId() );
        comment.setProduct( prod );
        commentService.saveComment( comment );
        Map<String, Object> rp = new HashMap<>();
        rp.put("id", comment.getId());
        rp.put("message", comment.getMessage());
        rp.put("createdAt", comment.getCreatedAt());
        String username = account.getUsername();
        if(comment.getAnonymous()) {
            username = username.substring( 0, 3 ) + "*******";
        }
        rp.put("name", username);
        return new ResponseEntity<>(rp, HttpStatus.OK );
    }

    @RequestMapping( value = "/bulk/{orderId}", method = RequestMethod.POST )
    public ResponseEntity<?> saveComments(@PathVariable String orderId, @RequestBody List<Comment> comments) {
        Account account = authenticatedUser.getUserDetails();
        List<Map<String, Object>> response = new ArrayList<>();
        for(Comment comment : comments) {
            Product prod = productService.getProductById( comment.getProduct().getId() );
            Order order = orderService.getOrderByOrderId( orderId );
            comment.setAccountId( account.getId() );
            comment.setProduct( prod );
            comment.setOrder( order );
            commentService.saveComment( comment );
            Map<String, Object> rp = new HashMap<>();
            rp.put("id", comment.getId());
            rp.put("message", comment.getMessage());
            rp.put("createdAt", comment.getCreatedAt());
            String username = account.getUsername();
            if(comment.getAnonymous()) {
                username = username.substring( 0, 3 ) + "*******";
            }
            rp.put("name", username);
            response.add( rp );
        }
        return new ResponseEntity<>(response, HttpStatus.OK );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getComments( @RequestParam( value = "productId" ) Integer productId,
                                        @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                        @RequestParam( value = "limit", defaultValue = "10" ) Integer limit ) {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );

        List<Object> results = new ArrayList<>();
        Page<Comment> comment = commentService.getComments(productId , pageable );
        for(Comment c : comment.getContent()) {
            Account account = accountService.getAccountById(c.getAccountId());
            Map<String, Object> rp = new HashMap<>();
            rp.put("comment", c);
            String username = account.getUsername();
            if(c.getAnonymous()) {
                username = username.substring( 0, 3 ) + "*******";
            }
            rp.put("name", username);
            results.add(rp);
        }
        response.put("data", results);
        response.put("currentPage", comment.getNumber());
        response.put("totalItems", comment.getTotalElements());
        response.put("totalPages", comment.getTotalPages());

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public ResponseEntity<?> deleteComment( @PathVariable int id ) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.deleteComment( id );
        } catch ( Exception e ) {
            response.put("message", "Server Error. Please try again later.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
        }
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
