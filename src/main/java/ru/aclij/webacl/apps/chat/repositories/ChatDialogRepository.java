package ru.aclij.webacl.apps.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aclij.webacl.apps.chat.entities.ChatDialog;

@Repository
public interface ChatDialogRepository extends JpaRepository<ChatDialog, Long> {
    ChatDialog getBySessionId(String sessionId);
}
