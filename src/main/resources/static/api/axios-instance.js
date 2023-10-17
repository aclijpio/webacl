import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000
})

instance.interceptors.request.use(
    (config) => {
        const jwtToken= localStorage.getItem("jwtToken");
        if (jwtToken){
            config.headers['Authorization'] = "Bearer ${jwtToken}";
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
)

export default instance;