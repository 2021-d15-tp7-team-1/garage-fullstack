import axios from 'axios';
import React, { Component } from 'react';
import LoginService from '../services/LoginService';

class LoginComp extends Component {
    constructor(props){
        super(props)
        this.state = {username: "", password: "", bconnect: false};
    }

    onChange = (event) => {
        this.setState({
            [event.target.name] : event.target.value
        })
    }

    onSubmit = (event) => {
        event.preventDefault();
        LoginService.tryLogin(this.state.username, this.state.password)
        .then(response => {
            console.log(response);
            this.setState({bconnect: true});
        }).catch(error => {
            console.log(error);
            this.setState({bconnect: false});
        });;
    }

    render() {
        return (
            <div>
                {!this.state.bconnect &&
                    <div class="container">
                        <form class="form-signin" onSubmit={this.onSubmit}>
                            <div className="form-group">
                                <label for="username">Nom d'utilisateur</label>
                                <input name="username" id="username" type="text" className="form-control" placeholder="Enter username"
                                    value={this.state.username} onChange={this.onChange} />
                            </div>

                            <div className="form-group">
                                <label for="password">Mot de passe</label>
                                <input name="password" id="password" type="password" className="form-control" placeholder="Enter password"
                                    value={this.state.password} onChange={this.onChange}/>
                            </div>
                            <button type="submit" className="btn btn-primary btn-block">Se connecter</button>
                        </form>
                    </div>
                }
                {this.state.bconnect &&
                <div>
                    <h1>Vous etes connecté</h1>
                </div>

                }
            </div>
        );
    }
}

export default LoginComp;