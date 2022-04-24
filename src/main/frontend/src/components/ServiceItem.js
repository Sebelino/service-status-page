import React, {Component} from 'react'

export default class ServiceItem extends Component {
    render() {
        const {id, name, url, createdAt, status, handleDoneTask, completed} = this.props

        return (<li className="list-group-item d-flex justify-content-between my-2">
            <h6 className={`mt-1 mb-0 align-middle ${completed ? 'completed-task' : ''}`}>{createdAt}</h6>
            <h6 className={`mt-1 mb-0 align-middle ${completed ? 'completed-task' : ''}`}>{name}</h6>
            <h6 className={`mt-1 mb-0 align-middle ${completed ? 'completed-task' : ''}`}>{url}</h6>
            <h6 className={`mt-1 mb-0 align-middle ${completed ? 'completed-task' : ''}`}>{status}</h6>
            <div className="todo-icon">
                    <span
                        className={`mx-2 ${completed ? 'text-success' : 'text-secondary'}`}
                        onClick={() => handleDoneTask(id)}
                    >
                        <i className={`${completed ? 'far fa-check-square' : 'far fa-square'}`}/>
                    </span>
            </div>
        </li>)
    }
}
