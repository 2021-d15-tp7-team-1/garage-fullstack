import React, { Component } from 'react';
import VehiculeService from '../services/VehiculeService';

class VehiculesComp extends Component {
    constructor(props) {
        super(props);
        this.state = {vehicules: []};
    }

    componentDidMount() {

        VehiculeService.getVehicules().then(
            (res) => {
                this.setState({vehicules : res.data})
            }
        );
    }

    render() {
        if(this.state.vehicules.length<1) {
            return "Aucun véhicule en stock !"
        }
        return (
            <div className="container">
                <br/>
                <h1 align="center">Liste des véhicules en stock</h1>
                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th>Marque</th>
                            <th>Modèle</th>
                            <th>Etat</th>
                            <th>Prix</th>
                            <th>Prix facturé</th>
                            <th>Quantité</th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.vehicules.map(data => 
                        <tr>
                            <td>{data.marque}</td>
                            <td>{data.modele}</td>
                            <td>{data.etat}</td>
                            <td>{data.prix}</td>
                            <td>{data.prix_facture}</td>
                            <td>{data.quantite_stock}</td>
                        </tr>)}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default VehiculesComp;