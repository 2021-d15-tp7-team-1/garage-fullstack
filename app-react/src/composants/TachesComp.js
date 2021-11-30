import React, { Component } from 'react';
import TacheService from '../services/TacheService';

class TachesComp extends Component {
    constructor(props) {
        super(props);
        this.state = {taches: []};
    }
    componentDidMount() {
        console.log("comp mounted")
        TacheService.findByIdMecanicien(4).then(
            (data) => {
                this.setState({taches : data})
            }
        )
    }
    render() {
        if(this.state.taches.length<1) {
            return "Aucunes tache attribuée !"
        }
        return (
            <div>
                <ul>
                {this.state.taches.map(data => <li>{data.intitule} {data.isTerminee}</li>)}
                </ul>
                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th>N°</th>
                            <th>Intitulé</th>
                            <th>Type</th>
                            <th>Pièces nécessaires</th>
                            <th>Priorité</th>
                            <th>Etat</th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.taches.map(data => 
                        <tr>
                            <td>{data.id}</td>
                            <td>{data.intitule}</td>
                            <td>{data.type.name}</td>
                            <td>{data.pieces.map(p => <span>{p.nomPiece}</span>)}</td>
                            <td>{data.priorite.name}</td>
                            <td>{data.isTerminee}</td>
                        </tr>)}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default TachesComp;