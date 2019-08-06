import React from "react";
import List from '../components/List'
import {getUsersByApp} from '../auth/UserApi'


class Dashboard extends React.Component {
    constructor(props) {
        super(props)
        this.state = {userList: []}
    }

    componentDidMount() {
        getUsersByApp(this.props.app, this.props.token)
            .then(res => this.setState({userList: res.data}))
    }

    render() {
        return (
            <div>
                <nav className="navbar" role="navigation" aria-label="main navigation">
                    <div className="navbar-brand">
                        <h2>{this.props.app}</h2>

                        <a role="button" className="navbar-burger" aria-label="menu" aria-expanded="false">
                            <span aria-hidden="true"></span>
                            <span aria-hidden="true"></span>
                            <span aria-hidden="true"></span>
                        </a>
                    </div>
                </nav>
                <div className="columns">
                    <div className="column">
                        First column
                    </div>
                    <div className="column">
                        <List data={this.state.userList}/>
                    </div>
                    <div className="column">
                        Fourth column
                    </div>
                </div>
            </div>
        )
    }
}

export default Dashboard;