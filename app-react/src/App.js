import logo from './logo.svg';
import './App.css';
import React from "react";
import { Link,Route,Switch } from "react-router-dom";
/** Importation du FrameWork Boostrap 4 */
import 'bootstrap/dist/css/bootstrap.css';
import TachesComp from './composants/TachesComp';
import LoginComp from './composants/LoginComp';
//import 'bootstrap/dist/js/bootstrap.bundle';

function App() {
  return (
    <div>
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <a className="navbar-brand" href="#"><strong>Super garage</strong></a>
      <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav">
                <li className="nav-item"><Link className="nav-link" to="/taches">Mes taches</Link></li>
                <li className="nav-item dropdown">
                    <a className="nav-link dropdown-toggle" data-toggle="dropdown" href="#">Stocks</a>
                    <div className="dropdown-menu">
                        <Link className="dropdown-item" to="/pieces">Pièces & Articles</Link>
                        <Link className="dropdown-item" to="/vehicules">Véhicules</Link>
                    </div>
                </li>
                <li class="nav-item"><Link className="nav-link" href="#">Ventes</Link></li>
            </ul>

        </div>
        <form action="/logout" method="post">
            <button type="submit" className="btn btn-secondary mb-2 float-right">Logout</button>
        </form>
    </nav>

    <Switch>
      <Route path="/" exact component={LoginComp}/>
      <Route path="/taches" component={TachesComp}/>
      <Route path="/pieces" component=""/>
      <Route path="/vehicules" component=""/>
      <Route path="/ventes" component=""/>
    </Switch>
    </div>
    
  );
}

export default App;
