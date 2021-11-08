package com.js.freeproject.domain.board.domain;

import com.js.freeproject.domain.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = {"comments"})
    Optional<Board> findById(Long id);

    @EntityGraph(attributePaths = {"user"})
    List<Board> findAll();

    @Query("select b from Board b where b.user = :user")
    List<Board> findByUserID(@Param("user") User user);
}
