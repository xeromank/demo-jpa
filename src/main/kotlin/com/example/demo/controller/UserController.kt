package com.example.demo.controller

import com.example.demo.entity.User
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
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management API")
class UserController(private val userService: UserService) {

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved users",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = User::class))])
    ])
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved user",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = User::class))]),
        ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
    ])
    fun getUserById(@Parameter(description = "ID of the user to retrieve") @PathVariable id: Long): ResponseEntity<User> {
        return try {
            val user = userService.getUserById(id)
            ResponseEntity.ok(user)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User successfully created",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = User::class))])
    ])
    fun createUser(@Parameter(description = "User details") @RequestBody user: User): ResponseEntity<User> {
        val savedUser = userService.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Update an existing user's details")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User successfully updated",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = User::class))]),
        ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
    ])
    fun updateUser(
        @Parameter(description = "ID of the user to update") @PathVariable id: Long,
        @Parameter(description = "Updated user details") @RequestBody userDetails: User
    ): ResponseEntity<User> {
        return try {
            val updatedUser = userService.updateUser(id, userDetails)
            ResponseEntity.ok(updatedUser)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user by their ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "User successfully deleted"),
        ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
    ])
    fun deleteUser(@Parameter(description = "ID of the user to delete") @PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            userService.deleteUser(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}
