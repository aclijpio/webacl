const socketChat = new SockJS('/chat');
const stompClientChat = Stomp.over(socketChat);

const sendButton = document.getElementById('send-button')
const messageInput = document.getElementById('message')
const messagesForm = document.getElementById('message-form')
stompClientChat.connect({}, function (frame) {
    stompClientChat.subscribe('/topic/response', (message) => {
        showMessage(message);
    })
})

function showMessage(message) {
    const p = document.createElement('p');
    p.textContent = message;
    messagesForm.appendChild(p);
}
sendButton.addEventListener('click', () => {
    const message = messageInput.value;
    stompClientChat.send('/request/chat', {}, JSON.stringify(message))
})