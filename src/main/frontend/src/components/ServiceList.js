import React, {Component, Fragment} from 'react'
import ServiceItem from './ServiceItem'

export default class ServiceList extends Component {
    render() {
        const {services} = this.props

        return (<Fragment>
            <h4 className="text-center">
                Service List
            </h4>

            {services.length === 0 ? '' : <ul className="list-group my-5">
                {services.map(service => {
                    return (<ServiceItem
                        key={service.url}
                        id={service.url}
                        name={service.name}
                        url={service.url}
                        createdAt={service.createdAt}
                    />)
                })}
            </ul>}
        </Fragment>)
    }
}
