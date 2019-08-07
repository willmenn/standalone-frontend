import React from "react";
import List from '../components/List'
import {getUsersByApp} from '../auth/UserApi'
import CreateUser from '../components/CreateUser'

class Dashboard extends React.Component {
    constructor(props) {
        super(props)
        this.state = {userList: []}
        this.updateUsersList = this.updateUsersList.bind(this);
    }

    componentDidMount() {
        getUsersByApp(this.props.app, this.props.token)
            .then(res => this.setState({userList: res.data}))
    }

    updateUsersList() {
        getUsersByApp(this.props.app, this.props.token)
            .then(res => this.setState({userList: res.data}))
    }

    render() {
        return (
            <div>
                <nav className="navbar card" role="navigation" aria-label="main navigation"
                     style={{marginBottom: '5px'}}>
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
                        To be Implemented
                    </div>
                    <div className="column">
                        <List data={this.state.userList}/>
                    </div>
                    <div className="column">
                        <CreateUser
                            token={this.props.token}
                            app={this.props.app}
                            updateUsersList={this.updateUsersList}
                        />
                    </div>
                </div>
            </div>
        )
    }
}

export default Dashboard;