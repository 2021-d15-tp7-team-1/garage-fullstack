import axios from "axios";

export default class TacheService {
    static findByIdMecanicien(idMeca) {
        return axios.get("http://localhost:8090/rest/taches/all")
    }
}