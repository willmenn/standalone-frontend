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
            <div style={{background: '#F7F7F7'}}>
                <section className="hero is-dark" style={{marginBottom: '5px'}}>
                    <div className="hero-body">
                        <div className="container">
                            <h1 className="title">
                                Welcome to Auth Dashboard
                            </h1>
                            <h2 className="subtitle">
                                You are logged in to <b>group {this.props.app}</b>
                            </h2>
                        </div>
                    </div>
                </section>
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