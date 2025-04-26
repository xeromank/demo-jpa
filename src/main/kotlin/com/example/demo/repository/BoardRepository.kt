package com.example.demo.repository

import com.example.demo.entity.Board
import com.example.demo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long> {
    fun findByUser(user: User): List<Board>
    fun findByTitleContaining(title: String): List<Board>
}