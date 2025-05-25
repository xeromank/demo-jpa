package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: Long): User = userRepository.findById(id)
        .orElseThrow { NoSuchElementException("User not found with id: $id") }

    fun getUserByEmail(email: String): User? = userRepository.findByEmail(email)

    @Transactional
    fun createUser(user: User): User = userRepository.save(user)

    @Transactional
    fun updateUser(id: Long, userDetails: User): User {
        val existingUser = getUserById(id)
        val updatedUser = existingUser.copy(
            name = userDetails.name,
            email = userDetails.email
        )
        return userRepository.save(updatedUser)
    }

    @Transactional
    fun deleteUser(id: Long) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
        } else {
            throw NoSuchElementException("User not found with id: $id")
        }
    }
}
