import React from "react";

class Card extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="card">
                <div className="card-content">
                    <p className="title">
                        {JSON.stringify(this.props.data)}
                    </p>
                </div>
            </div>
        )
    }
}

export default Card;