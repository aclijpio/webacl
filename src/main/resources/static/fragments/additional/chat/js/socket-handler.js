const sendButton = document.getElementById('chat-send-button')
const messageInput = document.getElementById('chat-input')
const messagesForm = document.getElementById('messages-form')
const id = window.location.pathname.split('/').pop();
const sockJSChat = new SockJS('/chat');
const stompClientChat = Stomp.over(sockJSChat);
const subscribeUrl = '/topic/response/chat/' + id;
stompClientChat.connect({}, function (frame) {
   stompClientChat.subscribe(subscribeUrl, (chatMessage) => {
      showMessage(JSON.parse(chatMessage.body).text);
   })
})
function showMessage(message) {
   console.log(message)
   const p = document.createElement('p');
   p.textContent = message;
   messagesForm.appendChild(p);
}

sendButton.addEventListener('click', () => {
   const message = messageInput.value;
   if (!(message.length === 0)){
      const chatMessage = {
         text: message
      }
      stompClientChat.send('/request/chat/' + id, {}, JSON.stringify(chatMessage))
   }
});

