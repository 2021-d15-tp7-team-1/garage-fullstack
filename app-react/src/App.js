import logo from './logo.svg';
import './App.css';
import { Link,Route,Switch } from "react-router-dom";
/** Importation du FrameWork Boostrap 4 */
import 'bootstrap/dist/css/bootstrap.css';
//import 'bootstrap/dist/js/bootstrap.bundle';

function App() {
  return (
    <div>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="#"><strong>Super garage</strong></a>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav">
                <li class="nav-item"><Link className="nav-link" to="/taches">Mes taches</Link></li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">Stocks</a>
                    <div class="dropdown-menu">
                        <Link className="dropdown-item" to="/pieces">Pièces & Articles</Link>
                        <Link className="dropdown-item" to="/vehicules">Véhicules</Link>
                    </div>
                </li>
                <li class="nav-item"><Link className="nav-link" href="#">Ventes</Link></li>
            </ul>

        </div>
        <form action="/logout" method="post">
            <button type="submit" class="btn btn-secondary mb-2 float-right">Logout</button>
        </form>
    </nav>

    <Switch>
      <Route path="/" exact component=""/>
      <Route path="/taches" component=""/>
      <Route path="/pieces" component=""/>
      <Route path="/vehicules" component=""/>
      <Route path="/ventes" component=""/>
    </Switch>
    </div>
    
  );
}

export default App;
