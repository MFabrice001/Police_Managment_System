package com.police.management.police_management_system.repository;

import com.police.management.police_management_system.Model.EPasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<EPasswordResetToken, Long> {

    /**
     * Finds a password reset token by its token value.
     *
     * @param token the token string to search for
     * @return an optional password reset token
     */
    Optional<EPasswordResetToken> findByToken(String token);

    /**
     * Finds expired password reset tokens based on the expiry date.
     *
     * @param now the current date-time for comparison
     * @return a list of expired password reset tokens
     */
    List<EPasswordResetToken> findByExpiryDateBefore(LocalDateTime now);

    /**
     * Finds password reset tokens by the email associated with the user.
     *
     * @param email the email address of the user
     * @return a list of password reset tokens for the specified email
     */
    @Query("SELECT p FROM EPasswordResetToken p WHERE p.user.email = :email")
    List<EPasswordResetToken> findByUserEmail(@Param("email") String email);
}
