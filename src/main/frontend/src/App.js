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
        fetch("/status")
            .then(response => response.json())
            .then(payload => this.setState({services: payload.services}));
    }

    componentDidUpdate(prevProps, prevState) {
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

    tick = () => {
        this.setState({
            pollingCount: this.state.pollingCount + 1
        })
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
            name: this.state.newServiceName, url: this.state.newServiceUrl,
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
                        newServiceName={this.state.newServiceName}
                        newServiceUrl={this.state.newServiceUrl}
                        handleServiceNameChange={this.handleServiceNameChange}
                        handleServiceUrlChange={this.handleServiceUrlChange}
                        handleSubmit={this.handleSubmit}
                    />
                    <ServiceList
                        services={this.state.services}
                        clearList={this.clearList}
                        updateTodosToShow={this.updateTodosToShow}
                    />
                </div>
            </div>
            <div>newServiceName: [{this.state.newServiceName}]</div>
            <div>newServiceUrl: [{this.state.newServiceUrl}]</div>
            <div>Polling count: [{this.state.pollingCount}]</div>
        </div>);
    }
}

export default App;
