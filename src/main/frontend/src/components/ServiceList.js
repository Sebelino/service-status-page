import React, {Component, Fragment} from 'react'
import ServiceItem from './ServiceItem'

export default class ServiceList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            services: []
        }
    }

    componentDidMount() {
        fetch("/status")
            .then(response => response.json())
            .then(payload => this.setState({services: payload.services}));
    }

    render() {
        const {
            items, updateTodosToShow, clearList, handleDelete, handleEdit, handleDoneTask, handleDeleteDoneTasks
        } = this.props

        return (<Fragment>
            <h3 className="text-center">
                ServiceList
            </h3>

            {this.state.services.length === 0 ? '' : <ul className="list-group my-5">
                {this.state.services.map(item => {
                    return (<ServiceItem
                        key={item.id}
                        id={item.id}
                        name={item.name}
                        url={item.url}
                    />)
                })}
            </ul>}

            {items.length === 0 ? '' : <ul className="list-group my-5">
                {items.map(item => {
                    return (<ServiceItem
                        key={item.id}
                        id={item.id}
                        name={item.title}
                        completed={item.completed}
                        handleDelete={() => handleDelete(item.id)}
                        handleEdit={() => handleEdit(item.id)}
                        handleDoneTask={handleDoneTask}
                    />)
                })}
            </ul>}
        </Fragment>)
    }
}