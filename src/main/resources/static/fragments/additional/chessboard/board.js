const board = new ChessBoard(
    'board',
    {
        pieceTheme: '/fragments/additional/chessboard/img/chesspieces/{piece}.png',
        draggable: true,
        onDrop: onDrop,
        onDragStart: onDragStart
    }
)
let sessionId;
let color;
let time;
function getGameInfo(){
    $.ajax({
        url: '/chess/playerInfo/' + id,
        method: 'POST',
        dataType: 'json',
        success: (data) => {

            header.sessionId = data.sessionId;
            sessionInfo.color = data.color.toLowerCase();
            sessionInfo.time = data.time;
            board.orientation(sessionInfo.color);
            updateGameInfo(data.gameInfo);
        },
        error : (error) => {
            console.error('Error ' + error);
        }
    })
}
const header = {
    sessionId
}
const sessionInfo = {
    color,
    time
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
    stompClientChat.send('/request/chess/' + id, header, JSON.stringify(move))
}

const firstTime = document.getElementById("first-time")
const secondTime = document.getElementById("second-time")

function updateBoard(chessBoard){
    board.position(chessBoard, false);
}
function updateGameInfo(gameInfo){
    updateBoard(gameInfo.fenCode);
    updateTimeAndStart(gameInfo)

}
function updateTimeAndStart(gameInfo){
    firstTime.textContent = getTime(sessionInfo.time - gameInfo.firstPlayer.currentTime);
    secondTime.textContent = getTime(sessionInfo.time - gameInfo.secondPlayer.currentTime);
}
let intervalId;
let timeLeft = 0;

function startTimer(seconds) {
    if (!intervalId) {
        timeLeft = seconds;
        updateTimerDisplay();
        intervalId = setInterval(decrementTimer, 1000);
    }
}
function decrementTimer() {
    if (timeLeft > 0) {
        timeLeft--;
        updateTimerDisplay();
    } else {
        stopTimer();
    }
}
function stopTimer() {
    clearInterval(intervalId);
    intervalId = null;
}
function updateTimerDisplay(timer) {
    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;
    const displayMinutes = String(minutes).padStart(2, '0');
    const displaySeconds = String(seconds).padStart(2, '0');
    timer.textContent = displayMinutes + ':' + displaySeconds;
}
function getTime(time){
    const dateTime = new Date(time);
    return dateTime.getMinutes().toString().padStart(2, '0') + ':' + dateTime.getSeconds().toString().padStart(2, '0');
}







