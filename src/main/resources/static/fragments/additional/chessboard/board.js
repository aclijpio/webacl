const board = new ChessBoard(
    'board',
    {
        pieceTheme: '/fragments/additional/chessboard/img/chesspieces/{piece}.png',
        draggable: true,
        onDrop: onDrop,
        onDragStart: onDragStart
    }
)

function getGameInfo(){
    $.ajax({
        url: '/chess/playerInfo/' + id,
        method: 'POST',
        dataType: 'json',
        success: (data) => {
            updateGameInfo(data)
        },
        error : (error) => {
            console.error('Error ' + error);
        }
    })
}
getGameInfo();
function onDragStart(piece){
    return true;
}
function onDrop (source, target) {
    const move = {
        source: source,
        target: target
    }
    sendMove(move)
    return 'snapback';
}
const sockJsChess = new SockJS('/ws/chess');
const stompClientChess = Stomp.over(sockJsChess);

const subscribeUrlChess = '/topic/response/chess/'+ id;
function handleGameInfoMessage(message){
    const gameInfo = JSON.parse(message.body);
    updateGameInfo(gameInfo)
}
stompClientChess.connect({}, (message) => {
    stompClientChess.subscribe(subscribeUrlChess, handleGameInfoMessage)
});
function sendMove(move){
    stompClientChat.send('/request/chess/' + id, {}, JSON.stringify(move))
}

const firstTime = document.getElementById("first-time")
const secondTime = document.getElementById("second-time")

function updateBoard(chessBoard){
    board.position(chessBoard, false);
}
function updateGameInfo(gameInfo){
    updateBoard(gameInfo.fenCode);
    firstTime.textContent = gameInfo.firstPlayer.currentTime;
    secondTime.textContent = gameInfo.secondPlayer.currentTime;
}






