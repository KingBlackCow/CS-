package com.js.freeproject.domain.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.js.freeproject.domain.board.application.BoardService;
import com.js.freeproject.domain.board.domain.Board;
import com.js.freeproject.domain.board.dto.BoardListResponse;
import com.js.freeproject.domain.board.dto.BoardRequest;
import com.js.freeproject.domain.board.dto.BoardResponse;
import com.js.freeproject.domain.board.dto.BoardSaveResponse;
import com.js.freeproject.domain.board.dto.BoardUserResponse;
import com.js.freeproject.domain.exhandler.ErrorResult;
import com.js.freeproject.domain.file.application.BoardFileService;
import com.js.freeproject.domain.file.domain.BoardFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/board")
public class BoardControlller {

    private final BoardService boardService;
    private final BoardFileService boardFileService;

    @ApiOperation(value = "게시판 글 작성")
    @PostMapping("/{userId}")
    public ResponseEntity<?> saveQuestion(final BoardRequest boardRequest, @PathVariable Long userId) throws IOException {
        Board board = boardService.saveQuestion(boardRequest, userId);
        final BoardSaveResponse boardSaveResponse = new BoardSaveResponse(board);
        return ResponseEntity.ok().body(boardSaveResponse);
    }

    @ApiOperation(value = "게시판 컨텐츠 조회")
    @GetMapping("/content/{boardId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "해당 게시판이 존재하지 않습니다.", response = ErrorResult.class),
            @ApiResponse(code = 500, message = "게시판컨텐츠가 존재하지 않습니다.", response = ErrorResult.class)
    })
    public ResponseEntity getBoardContent(@PathVariable Long boardId) {
        BoardResponse boardResponse = boardService.findById(boardId);
        List<BoardFile> boardFiles = boardResponse.getBoardFiles();
        if (boardFiles.isEmpty()) {
            throw new IllegalStateException("게시판컨텐츠가 존재하지 않습니다.");
        }
        return ResponseEntity.ok().body(boardFiles);
    }

    @ApiOperation(value = "게시판 목록 조회")
    @GetMapping
    public ResponseEntity<?> getBoardList() {
        List<BoardListResponse> boardListResponses = boardService.findAllBoard();
        return ResponseEntity.ok().body(boardListResponses);
    }

    @ApiOperation(value = "게시판 선택 항목 조회")
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardOne(@PathVariable Long boardId) {
        BoardResponse boardResponse = boardService.findById(boardId);
        return ResponseEntity.ok().body(boardResponse);
    }

    @ApiOperation(value = "게시판 글 수정")
    @PutMapping("/{boardId}")
    public ResponseEntity<?> putBoard(final BoardRequest boardRequest, @PathVariable Long boardId) throws IOException {
        boardService.updateDescription(boardRequest, boardId);
        return ResponseEntity.ok().body("ok");
    }

    @ApiOperation(value = "게시판 유저가 쓴 글 조회")
    @GetMapping("/user/{userId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "유저가 존재하지 않습니다.", response = ErrorResult.class),
            @ApiResponse(code = 500, message = "유저가 쓴 글이 존재하지 않습니다.", response = ErrorResult.class)
    })
    public ResponseEntity getBoardByUser(@PathVariable Long userId) {
        List<BoardUserResponse> boards = boardService.findByUserId(userId);
        return ResponseEntity.ok().body(boards);
    }

}
