package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.*;
import com.example.online_shopping.database.repository.CommentsRepo;
import com.example.online_shopping.database.repository.UsersRepository;
import com.example.online_shopping.erroe.SystemError;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.comment.CommentRequest;
import com.example.online_shopping.response.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentServices {

    private final UsersRepository usersRepository;

    private final CommentsRepo commentsRepo;

    @Autowired
    public CommentServices(UsersRepository usersRepository, CommentsRepo commentsRepo) {
        this.usersRepository = usersRepository;
        this.commentsRepo = commentsRepo;
    }


    public List<Comments> getCommentUser(Long user_id) {

        try {
            return commentsRepo.findAllByTypeID(user_id);
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-115, "Database error");
            throw new SystemError("get comment", -1, error);
        }
    }


    public List<Comments> getCommentUserMe(String username) {

        try {
            Users user = usersRepository.findByUsername(username);
            return commentsRepo.findAllByTypeID(user.getUser_id());
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-115, "Database error");
            throw new SystemError("get comment", -1, error);
        }
    }


    public List<Comments> creareCommentAdmin(CommentRequest comment, Long user_id, String username) throws SystemError {

        try {

            Users user = usersRepository.findByUsername(username);
            Comments comments = new Comments(user_id, user, comment.getMessage(), comment.getAnswer());
            commentsRepo.save(comments);

            log.info("Create Message: {}", comments.toString());
            return commentsRepo.findAllByTypeID(user.getUser_id());
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-115, "The comment has not been saved!");
            throw new SystemError("comment saved", -1, error);
        }
    }


    public List<Comments> creareCommentUser(CommentRequest comment, String username) throws SystemError {

        try {

            Users user = usersRepository.findByUsername(username);
            Comments comments = new Comments(user.getUser_id(), user, comment.getMessage(), comment.getAnswer());
            commentsRepo.save(comments);

            log.info("Create Message: {}", comments.toString());
            return commentsRepo.findAllByTypeID(user.getUser_id());
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-115, "The comment has not been saved!");
            throw new SystemError("comment saved", -1, error);
        }
    }


    public List<Comments> editComment(Long comment_id, CommentRequest comment, String username) throws SystemError {

        try {
            Users user = usersRepository.findByUsername(username);
            Comments comments = commentsRepo.findByCommentIdAndUserId(comment_id, user.getUser_id());
            comments.setMessage(comment.getMessage());

            commentsRepo.save(comments);
            log.info("Update Comments: {}", comments.toString());

            return commentsRepo.findAllByTypeID(user.getUser_id());
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-116, "The comment has not been changed.");
            throw new SystemError("Edit comment", -1, error);
        }
    }


    /*public Message creareProductComment(ProductCommentRequest comment, String token) throws SystemError {

        filter.checkMessage(comment.getMessage());

        Members member = filter.checkMember(token);

        Products product = filter.checkProduct(comment.getProduct_id());

        try {
            ProductComments comments = new ProductComments(member.getRole(), member, null, product, comment.getMessage(), comment.getAnswer(), new Date());
            productCommentRepo.save(comments);

            log.info("Create Product Comment: {}", comments.toString());

            Message message = new Message(200, "The product comment has been saved!");
            return message;
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-115, "The product comment has not be saved!");
            throw new SystemError("Product comment saved", -1, error);
        }
    }


    public Message editProductComment(Long comment_id, ProductCommentRequest com, String token) throws SystemError {

        filter.checkMessage(com.getMessage());

        Members member = filter.checkMember(token);

        ProductComments productComments = filter.checkProductComment(comment_id, member.getMember_id());

        try {
            productComments.setMessage(com.getMessage());
            productComments.setEdit_date(new Date());

            productCommentRepo.save(productComments);

            log.info("Update Product Comment: {}", productComments.toString());

            Message message = new Message(200, "The product comment has been changed");
            return message;
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-115, "The product comment has not been changed");
            throw new SystemError("Edit product comment", -1, error);
        }

    }


    public Message deleteComment(Long comment_id, String token) throws SystemError {

        Members member = filter.checkMember(token);

        Comments comments = filter.checkComment(comment_id, member.getMember_id());

        try {
            commentsRepo.deleteById(comments.getComment_id());

            log.info("Delete Comments: {}", comments.toString());

            Message message = new Message(200, "The comment been deleted");
            return message;
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-116, "The comment has not been deleted.");
            throw new SystemError("Delete comment", -1, error);
        }
    }


    public Message deleteProductComment(Long comment_id, String token) throws SystemError {

        Members member = filter.checkMember(token);

        ProductComments comments = filter.checkProductComment(comment_id, member.getMember_id());

        try {
            productCommentRepo.deleteById(comments.getProduct_comment_id());

            log.info("Delete Product Comments: {}", comments.toString());

            Message message = new Message(200, "The product comment been deleted");
            return message;
        } catch (Exception e) {
            log.error("Exception e: {}", e);
            ErrorMessage error = new ErrorMessage(-116, "The product comment has not been deleted.");
            throw new SystemError("Delete product comment", -1, error);
        }
    }*/

}
