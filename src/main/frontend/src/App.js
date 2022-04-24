import React, {Component} from 'react'
import {v1 as uuid} from 'uuid'
import ServiceList from "./components/ServiceList";
import ServiceInput from "./components/ServiceInput";

class App extends Component {
    constructor(props) {
        super(props)
        this.state = {
            items: [], itemsToShow: "all", id: uuid(), item: '', services: [], newService: ''
        }
    }

    componentDidMount() {
        console.log("componentDidMount")
        fetch("/status")
            .then(response => response.json())
            .then(payload => this.setState({services: payload.services}));
    }


    handleChange = event => {
        this.setState({
            newService: event.target.value
        })
    }

    handleSubmit = event => {
        event.preventDefault()

        const addedService = {
            name: 'HELLO'
        }

        const updatedServices = [...this.state.services, addedService]

        if (this.state.newService.length > 0) {
            this.setState({
                services: updatedServices, id: uuid()
            })
        }
    }

    updateTodosToShow = string => {
        this.setState({
            itemsToShow: string
        });
    };

    clearList = () => {
        this.setState({
            items: []
        })
    }

    render() {
        return (<div className="container">
            <div className="row">
                <div className="col-10 col-md-8 mx-auto mt-4">
                    <h3 className="text-capitalize text-center">Service Status Page</h3>
                    <ServiceInput
                        item={this.state.newService}
                        handleChange={this.handleChange}
                        handleSubmit={this.handleSubmit}
                    />
                    <ServiceList
                        services={this.state.services}
                        clearList={this.clearList}
                        updateTodosToShow={this.updateTodosToShow}
                    />
                </div>
            </div>
            <div>newService: [{this.state.newService}]</div>
        </div>);
    }
}

export default App;
