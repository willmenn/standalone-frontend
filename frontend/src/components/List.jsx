import React from "react";
import Card from './Card'

class List extends React.Component {
    constructor(props) {
        super(props)
    }


    render() {
        return (
            <ul>
                {this.props.data.map(data =>
                    <li>
                        <Card data={data}/>
                    </li>)}
            </ul>
        )
    }
}

export default List;