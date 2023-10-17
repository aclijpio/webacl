import axios from './axios-instance';

export const authenticate = (username, password) => {
    const data = {
        username: username,
        password: password,
    };

    return axios.post('/auth', data)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            throw error;
        });
};
