import React from 'react'
   import {useEffect,useState} from 'react'
import { useParams } from 'react-router-dom';
import ReactDOM from 'react-dom'
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';

export const MatchPage=() => {

   

if(!team || !team.teamName){
  return <h1>Team not found</h1>
}

  return (
    <div className="MatchPage">

    </div>
  );
}


