import React from "react";

class Card extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="card">
                <div className="card-content">
                    <p className="subtitle">
                        Username: {this.props.data.USERNAME}
                        <br/>
                        Role: {this.props.data.ROLE}
                    </p>
                </div>
            </div>
        )
    }
}

export default Card;