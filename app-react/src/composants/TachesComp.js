import React, { Component } from 'react';
import TacheService from '../services/TacheService';

class TachesComp extends Component {
    constructor(props) {
        super(props);
        this.state = {taches: []};
    }
    componentDidMount() {
        console.log("comp mounted")
        /*
        TacheService.findByIdMecanicien(4).then(
            (res) => {
                this.setState({taches : res.data})
            }
        )*/

        TacheService.getFiches().then(
            (res) => {
                this.setState({taches : res.data})
            }
        )
        console.log("taches : " + this.state.taches)
        
    }

    onTerminerTache(id){
        TacheService.terminerTache(id).then(
            (res) => {
                console.log("tache terminer")
            }
        ).catch(error => {
            console.log(error);
        });
    }
    render() {
        if(this.state.taches.length<1) {
            return "Aucunes tache attribuée !"
        }
        return (
            <div>
                <br/>
                <h1>Liste de vos taches</h1>
                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th>N°</th>
                            <th>Intitulé</th>
                            <th>Type</th>
                            <th>Pièces nécessaires</th>
                            <th>Priorité</th>
                            <th>Etat</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.taches.map(data => 
                        <tr>
                            <td>{data.id}</td>
                            <td>{data.intitule}</td>
                            <td>{data.type}</td>
                            <td>{data.pieces.map(p => <span className="mr-2">{p.nom}</span>)}</td>
                            <td>{data.priorite}</td>
                            
                            {!data.isTerminee &&
                                <td>A faire</td>
                            }
                            {data.isTerminee &&
                                <td>Terminée</td>
                            }

                            {!data.isTerminee &&
                                <td><button class="btn btn-success mb-2" onClick={(e) => this.onTerminerTache(data.id, e)}>Terminer</button></td>
                            }
                            
                        </tr>)}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default TachesComp;