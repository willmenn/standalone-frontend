import React from "react";
import Card from './Card'

class List extends React.Component {
    constructor(props) {
        super(props)
    }

    comparar(a, b) {
        if (a.USERNAME < b.USERNAME) {
            return -1;
        }
        if (a.USERNAME > b.USERNAME) {
            return 1;
        }

        return 0;
    }

    render() {
        return (
            <ul>
                {this.props.data.sort(this.comparar).map(data =>
                    <li>
                        <Card data={data}/>
                    </li>)}
            </ul>
        )
    }
}

export default List;