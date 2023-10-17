package ru.aclij.webacl.apps.chess;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aclij.webacl.apps.chess.supp.UniqueIdGame;

@Service
@RequiredArgsConstructor
public class ChessService {
    UniqueIdGame uniqueIdGame;

    public String generateGame(){
        return uniqueIdGame.generateUniqueId();
    }
    public void joinTheGameById(String id){

    }
}
