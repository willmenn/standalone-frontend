import axios from 'axios';

const APP_TOKEN = 1;

export const authUser = (username, password) => {
    return axios.post('/auth',
        {username: username, password: password, appToken: APP_TOKEN})
}
