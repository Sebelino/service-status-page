import React, {Component} from 'react'
import {v1 as uuid} from 'uuid'
import ServiceList from "./components/ServiceList";
import ServiceInput from "./components/ServiceInput";

class App extends Component {
    constructor(props) {
        super(props)
        this.state = {
            items: [],
            itemsToShow: "all",
            id: uuid(),
            item: '',
            services: [],
            newServiceName: '',
            newServiceUrl: '',
            pollingCount: 0,
            delay: 3000,
        }
    }

    componentDidMount() {
        console.log("componentDidMount")
        this.interval = setInterval(this.tick, this.state.delay)
        this.refreshServices()
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        console.log("componentDidUpdate")
        if (prevState.delay !== this.state.delay) {
            clearInterval(this.interval)
            this.interval = setInterval(this.tick, this.state.delay)
        }
    }

    componentWillUnmount() {
        console.log("componentWillUnmount")
        clearInterval(this.interval)
    }

    refreshServices() {
        fetch("/status")
            .then(response => response.json())
            .then(payload => this.setState({services: payload.services}));
    }

    tick = () => {
        this.setState({
            pollingCount: this.state.pollingCount + 1
        })
        this.refreshServices()
    }

    handleServiceNameChange = event => {
        this.setState({
            newServiceName: event.target.value
        })
    }

    handleServiceUrlChange = event => {
        this.setState({
            newServiceUrl: event.target.value
        })
    }

    handleSubmit = event => {
        event.preventDefault()

        const addedService = {
            name: this.state.newServiceName, url: this.state.newServiceUrl, status: "UNKNOWN",
        }

        const requestOptions = {
            method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(addedService)
        };
        fetch('/status', requestOptions)
            .then(response => console.log("POST /status"));

        const updatedServices = [...this.state.services, addedService]

        if (this.state.newServiceName.length > 0) {
            this.setState({
                services: updatedServices, id: uuid(), newServiceName: '', newServiceUrl: '',
            })
        }
    }

    render() {
        return (<div className="container">
            <div className="row">
                <div className="col-10 col-md-8 mx-auto mt-4">
                    <h3 className="text-capitalize text-center">Service Status Page</h3>
                    <ServiceInput
                        newServiceName={this.state.newServiceName}
                        newServiceUrl={this.state.newServiceUrl}
                        handleServiceNameChange={this.handleServiceNameChange}
                        handleServiceUrlChange={this.handleServiceUrlChange}
                        handleSubmit={this.handleSubmit}
                    />
                    <ServiceList
                        services={this.state.services}
                    />
                </div>
            </div>
        </div>);
    }
}

export default App;
