const board = new ChessBoard('board', {
    position: 'start',
    pieceTheme: "/chessgame/board/img/chesspieces/{piece}.png",
    draggable: true,
    showNotation: true,
    onDrop: onDrop
});
const socketChess= new SockJS('/projects/chess')
const stompClientChess = Stomp.over(socketChess)
function onDrop(source, target){
    sendMove(source, target)
}
stompClientChess.connect({}, function (frame){
    stompClientChess.subscribe('/topic/response', function (response){
        const message = JSON.parse(response.body)
        if (message.move){
            const move = message.move
            board.move(move.source, move.target)
        }
    })
})
function sendMove(source, target){
    const move = {
        source: source,
        target: target
    }
    stompClientChess.send("/request/move", {}, JSON.stringify(move));
}
function startGame(){
    stompClientChess.send("/request/start", {})
}
function surrendered(){
    stompClientChess.send("/request/surrender", {})
}