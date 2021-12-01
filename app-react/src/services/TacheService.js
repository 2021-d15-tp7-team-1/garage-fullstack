import axios from "axios";

export default class TacheService {
    static findByIdMecanicien(idMeca) {
        return axios.get("http://localhost:8090/rest/taches/all")
    }

    static terminerTache(idTache){
        return axios.get("http://localhost:8090/rest/taches/terminer-tache/{idTache}")
    }

    static getFiches(){
        return axios.get("http://localhost:3000/taches")
    }
}