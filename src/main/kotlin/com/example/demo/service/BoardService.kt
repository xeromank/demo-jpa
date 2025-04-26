package com.example.demo.service

import com.example.demo.entity.Board
import com.example.demo.entity.User
import com.example.demo.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BoardService(private val boardRepository: BoardRepository) {

    fun getAllBoards(): List<Board> = boardRepository.findAll()

    fun getBoardById(id: Long): Board = boardRepository.findById(id)
        .orElseThrow { NoSuchElementException("Board not found with id: $id") }

    fun getBoardsByUser(user: User): List<Board> = boardRepository.findByUser(user)

    fun searchBoardsByTitle(title: String): List<Board> = boardRepository.findByTitleContaining(title)

    @Transactional
    fun createBoard(board: Board): Board = boardRepository.save(board)

    @Transactional
    fun updateBoard(id: Long, boardDetails: Board): Board {
        val existingBoard = getBoardById(id)
        val updatedBoard = existingBoard.copy(
            title = boardDetails.title,
            content = boardDetails.content,
            updatedAt = LocalDateTime.now()
        )
        return boardRepository.save(updatedBoard)
    }

    @Transactional
    fun deleteBoard(id: Long) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id)
        } else {
            throw NoSuchElementException("Board not found with id: $id")
        }
    }
}