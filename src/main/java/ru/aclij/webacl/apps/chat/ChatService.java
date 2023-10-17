package ru.aclij.webacl.apps.chat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.aclij.webacl.apps.chat.entities.ChatDialog;
import ru.aclij.webacl.apps.chat.entities.ChatMessage;
import ru.aclij.webacl.apps.chat.exceptions.DialogNotFoundException;
import ru.aclij.webacl.apps.chat.repositories.ChatDialogRepository;
import ru.aclij.webacl.apps.chat.repositories.ChatMessageRepository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatDialogRepository dialogRepository;
    private final ChatMessageRepository messageRepository;


    public void createDialog(@NotNull String sessionId){
        dialogRepository.save(new ChatDialog(sessionId));
    }
    private void saveDialog(@NotNull ChatDialog dialog){
        dialogRepository.save(dialog);
    }
    private ChatDialog getDialog(@NotNull String sessionId){
        return dialogRepository.getBySessionId(sessionId);
    }
    @Transactional
    public void addChatMessage(@NotNull String sessionId, @NotNull ChatMessage chatMessage) {
        ChatDialog dialog = getDialog(sessionId);
        if (dialog == null) throw new DialogNotFoundException("Dialog not found with session id " + sessionId);
        chatMessage.setChatDialog(dialog);
        dialog.getMessages().add(chatMessage);
        saveDialog(dialog);
    }
    @Transactional
    public List<ChatMessage> getMessages(@NotNull String sessionId){
        ChatDialog dialog= dialogRepository.getBySessionId(sessionId);
        if (dialog == null) throw new DialogNotFoundException("Dialog not found with session id " + sessionId);

        return dialog.getMessages();
    }
}
