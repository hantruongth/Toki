package com.toki.games.app.repository;

import com.toki.games.app.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
