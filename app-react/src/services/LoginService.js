import axios from "axios";

export default class LoginService {
    static tryLogin(username, password){
        return axios.post('http://localhost:8090/signin', username, password);
            
    }
}