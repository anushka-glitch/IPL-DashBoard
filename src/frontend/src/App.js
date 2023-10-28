import React from 'react'
import ReactDOM from 'react-dom'
import {Route, BrowserRouter as Router,Switch} from 'react-router-dom'
import logo from './logo.svg';
import './App.css';
import  {TeamPage} from './pages/TeamPage';
import {MatchPage} from './pages/MatchPage';

function App() {
  return (
    <div className="App">
      <Router>
     <Route path= "/teams/:teamName"> 
    <TeamPage />
    </Route>
    </Router>
    </div>
  );
}

export default App;
