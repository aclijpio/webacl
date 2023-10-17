const sendButton = document.getElementById('chat-send-button')
const messageInput = document.getElementById('chat-input')
const messagesForm = document.getElementById('messages-form')

const sockJSChat = new SockJS('/chat');
const stompClientChat = Stomp.over(sockJSChat);

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
   console.log(messageInput.textContent)
   const message = messageInput.value;
   if (!(message.length === 0)){
      stompClientChat.send('/request/chat', {}, JSON.stringify(message))
   }
});

