import axios from 'axios';

export const getUsersByApp = (app, token) => {
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
    return axios.get('/users/apps/' + app);
}

export const createUser = (body, token) => {
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
    return axios.post('/users', body);
}

