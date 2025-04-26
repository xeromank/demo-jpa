package com.example.demo.controller

import com.example.demo.entity.Board
import com.example.demo.entity.User
import com.example.demo.service.BoardService
import com.example.demo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/boards")
@Tag(name = "Board", description = "Board management API")
class BoardController(
    private val boardService: BoardService,
    private val userService: UserService
) {

    @GetMapping
    @Operation(summary = "Get all boards", description = "Retrieve a list of all boards")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved boards",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Board::class))])
    ])
    fun getAllBoards(): ResponseEntity<List<Board>> {
        val boards = boardService.getAllBoards()
        return ResponseEntity.ok(boards)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get board by ID", description = "Retrieve a board by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved board",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Board::class))]),
        ApiResponse(responseCode = "404", description = "Board not found", content = [Content()])
    ])
    fun getBoardById(@Parameter(description = "ID of the board to retrieve") @PathVariable id: Long): ResponseEntity<Board> {
        return try {
            val board = boardService.getBoardById(id)
            ResponseEntity.ok(board)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get boards by user ID", description = "Retrieve all boards created by a specific user")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved boards",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Board::class))]),
        ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
    ])
    fun getBoardsByUser(@Parameter(description = "ID of the user whose boards to retrieve") @PathVariable userId: Long): ResponseEntity<List<Board>> {
        return try {
            val user = userService.getUserById(userId)
            val boards = boardService.getBoardsByUser(user)
            ResponseEntity.ok(boards)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search boards by title", description = "Search for boards containing the specified title")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved boards",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Board::class))])
    ])
    fun searchBoardsByTitle(@Parameter(description = "Title to search for") @RequestParam title: String): ResponseEntity<List<Board>> {
        val boards = boardService.searchBoardsByTitle(title)
        return ResponseEntity.ok(boards)
    }

    @PostMapping
    @Operation(summary = "Create a new board", description = "Create a new board with the provided details")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Board successfully created",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Board::class))])
    ])
    fun createBoard(@Parameter(description = "Board details") @RequestBody board: Board): ResponseEntity<Board> {
        val savedBoard = boardService.createBoard(board)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBoard)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a board", description = "Update an existing board's details")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Board successfully updated",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Board::class))]),
        ApiResponse(responseCode = "404", description = "Board not found", content = [Content()])
    ])
    fun updateBoard(
        @Parameter(description = "ID of the board to update") @PathVariable id: Long,
        @Parameter(description = "Updated board details") @RequestBody boardDetails: Board
    ): ResponseEntity<Board> {
        return try {
            val updatedBoard = boardService.updateBoard(id, boardDetails)
            ResponseEntity.ok(updatedBoard)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a board", description = "Delete a board by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Board successfully deleted"),
        ApiResponse(responseCode = "404", description = "Board not found", content = [Content()])
    ])
    fun deleteBoard(@Parameter(description = "ID of the board to delete") @PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            boardService.deleteBoard(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}
