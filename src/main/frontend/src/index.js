import React from 'react'
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.css'
import './index.css'
import App from './App'
import {createRoot} from "react-dom/client";

const root = createRoot(document.getElementById('root'))
root.render(<App/>);