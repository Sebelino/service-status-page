import React, { Component } from 'react'
import {v1 as uuid} from 'uuid'

class App extends Component {
    constructor(props) {
        super(props)
        this.state={
            items: [],
            itemsToShow: "all",
            id: uuid(),
            item: '',
            editItem: false,
        }
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-10 col-md-8 mx-auto mt-4">
                        <h3 className="text-capitalize text-center">Hoy</h3>
                    </div>
                </div>
            </div>
        );
    }
}

export default App;
