//package com.js.freeproject.domain.comment;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.js.freeproject.domain.comment.dto.CommentRequest;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class CommentTest {
//
//    @Autowired
//    MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper OM;
//
//    @Test
//    @Order(1)
//    void saveComment() throws Exception {
//        CommentRequest cr = new CommentRequest();
//        cr.setContent("content test");
//        cr.setUserId((long)1);
//        cr.setParentId((long)0);
//        Long boardId = (long)1;
//
//        String comment = OM.writeValueAsString(cr);
//
//        mvc.perform(post("/api/v1/comment/"+1)
//                .content(comment)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//}
