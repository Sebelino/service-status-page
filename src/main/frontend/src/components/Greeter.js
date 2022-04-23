import React, {Component} from 'react'

export default class Greeter extends Component {

    constructor(props) {
        super(props);
        this.state = {
            message: "Default message"
        }
    }

    componentDidMount() {
        fetch("/status")
            .then(response => response.text())
            .then(text => this.setState({message: text}));
    }

    render() {
        return (<div>
            <span>{this.state.message}</span>
        </div>);
    }
}
