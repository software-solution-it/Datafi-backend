package com.data.fi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.data.fi.model.MagicLink;

@Repository
public interface MagicLinkRepository extends JpaRepository<MagicLink, Long> {
    Optional<MagicLink> findByToken(String token); // Método para encontrar o MagicLink pelo token
}
