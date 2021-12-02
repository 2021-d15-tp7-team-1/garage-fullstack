import axios from "axios";

export default class PieceService{
    static getPieces(){
        return axios.get("http://localhost:3000/pieces")
    }
}