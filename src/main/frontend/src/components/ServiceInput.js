import React, {Component} from 'react'

export default class ServiceInput extends Component {
    render() {
        const {newServiceName, newServiceUrl, handleServiceNameChange, handleServiceUrlChange, handleSubmit} = this.props

        return (<div className="card card-body my-3">
            <form onSubmit={handleSubmit}>
                <div className="input-group">
                    <div className="input-group-prepend">
                        <div className="input-group-text bg-info text-white">
                            <i className="fas fa-book"/>
                        </div>
                    </div>

                    <input
                        type="text"
                        className="form-control"
                        placeholder="Service name"
                        value={newServiceName}
                        onChange={handleServiceNameChange}
                    />
                    <input
                        type="text"
                        className="form-control"
                        placeholder="URL"
                        value={newServiceUrl}
                        onChange={handleServiceUrlChange}
                    />
                </div>

                <button
                    type="submit"
                    className={`btn btn-block mt-3 btn-info`}
                >
                    Add new service
                </button>
            </form>
        </div>)
    }
}
