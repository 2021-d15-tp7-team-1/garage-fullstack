import axios from "axios";

export default class VehiculeService{
    static getVehicules(){
        return axios.get("http://localhost:3000/vehicules")
    }
}