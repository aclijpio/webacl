const board = new ChessBoard('board', {
    position: 'start',
    pieceTheme: "/board/img/chesspieces/{piece}.png",
    draggable: true,
    showNotation: true,
    onDrop: onDrop
});
const socket = new SockJS('/chess')
const stompClient = Stomp.over(socket)
function onDrop(source, target){
    sendMove(source, target)
}
stompClient.connect({}, function (frame){
    stompClient.subscribe('/topic/send', function (response){
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
    stompClient.send("/response/move", {}, JSON.stringify(move));
}
function startGame(){
    stompClient.send("/response/start", {})
}
function surrendered(){
    stompClient.send("response/surrender", {})
}