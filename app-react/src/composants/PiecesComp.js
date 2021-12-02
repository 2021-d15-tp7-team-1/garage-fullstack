import React, { Component } from 'react';
import PieceService from '../services/PieceService';

class PiecesComp extends Component {
    constructor(props) {
        super(props);
        this.state = {pieces: []};
    }

    componentDidMount() {

        PieceService.getPieces().then(
            (res) => {
                this.setState({pieces : res.data})
            }
        );
    }

    render() {
        if(this.state.pieces.length<1) {
            return "Aucunes pièces en stock !"
        }
        return (
            <div>
                <br/>
                <h1 align="center">Liste des pièces en stock</h1>
                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th>N°</th>
                            <th>Nom</th>
                            <th>Type</th>
                            <th>Prix</th>
                            <th>Prix facturé</th>
                            <th>Quantité</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.pieces.map(data => 
                        <tr>
                            <td>{data.id}</td>
                            <td>{data.nom}</td>
                            <td>{data.type}</td>
                            <td>{data.prix}</td>
                            <td>{data.prix_facture}</td>
                            <td>{data.quantite_stock}</td>
                            <td>
                                <button className="btn btn-primary mb-2 mr-1">+</button>
                                <button className="btn btn-primary mb-2">-</button>
                            </td>
                        </tr>)}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default PiecesComp;