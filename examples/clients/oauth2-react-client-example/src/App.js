import React from 'react';
import './App.css';
import Secured from './components/Secured';

const App = () => {
  return (
    <div className="App">
      <Secured>
        You are authenticated!!!!
      </Secured>
    </div>
  );
};

export default App;
