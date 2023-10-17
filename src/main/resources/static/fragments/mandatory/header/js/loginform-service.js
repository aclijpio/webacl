import { authenticate } from '../../../../api/auth-service';
import axios from "axios";
document.addEventListener("DOMContentLoaded", () => {
    const usernameInput = document.getElementById("username")
    const passwordInput = document.getElementById("password")
    const formSubmit = document.getElementById("login-submit")

    authenticate(usernameInput.textContent,
                passwordInput.textContent)
        .then(data => {
            console.log(200)
            console.log(data)
        })
        .catch(error => {
            console.error(error)
        })
});
axios.get('/secured')
    .then(response => {
        console.log('Успешный ответ:', response.data);
    })
    .catch(error => {
        console.error('Ошибка:', error);
    });