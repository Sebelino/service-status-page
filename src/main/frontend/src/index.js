import React from 'react'
import ReactDOM from 'react-dom'
import 'bootstrap/dist/css/bootstrap.css'
import './index.css'
import App from './App'

ReactDOM.render(<App />, document.getElementById('root'));

//import React from 'react';
//import ReactDOM from 'react-dom';
//
//class Greeter extends React.Component {
//
//    constructor(props) {
//        super(props);
//        this.state = {
//            message: "Default message"
//        }
//    }
//
//    componentDidMount() {
//        fetch("/status")
//            .then(response => response.text())
//            .then(text => this.setState({message: text}));
//    }
//
//    render() {
//        return (<div>
//            <span>{this.state.message}</span>
//        </div>);
//    }
//}
//
//ReactDOM.render(<Greeter/>, document.getElementById('root'));
