package ru.aclij.webacl.apps.chess.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChessSessionRepository extends JpaRepository<ChessSession, Integer> {
    ChessSession findByKey(String key);
}
