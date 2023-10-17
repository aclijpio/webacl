const board = new ChessBoard(
    'board',
    {
        pieceTheme: '/fragments/additional/chessboard/img/chesspieces/{piece}.png',
        position: 'start',
        draggable: true,
        Drop: Drop
    }
)

const sockJsChess = new SockJs('/chess');
const stompClientChess = Stomp.over(sockJsChess);

stompClientChess.connect({}, (message) => {
    console.log(message);
});




