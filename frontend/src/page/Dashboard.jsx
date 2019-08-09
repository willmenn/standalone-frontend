import React from "react";
import List from '../components/List'
import {getUsersByApp} from '../auth/UserApi'
import CreateUser from '../components/CreateUser'

class Dashboard extends React.Component {
    constructor(props) {
        super(props)
        this.state = {userList: [], groupByRole: new Map()}
        this.updateUsersList = this.updateUsersList.bind(this);
    }

    componentDidMount() {
        getUsersByApp(this.props.app, this.props.token)
            .then(res => this.setState({userList: res.data}))
            .then(() => this.groupByRole())
    }

    updateUsersList() {
        getUsersByApp(this.props.app, this.props.token)
            .then(res => this.setState({userList: res.data}))
    }

    groupByRole() {
        let groupByRole = new Map();
        console.log(this.state.userList)
        this.state.userList
            .forEach(user => {
                if (groupByRole.has(user.ROLE)) {
                    groupByRole.set(user.ROLE, groupByRole.get(user.ROLE) + 1);
                } else {
                    groupByRole.set(user.ROLE, 1);
                }
            })
        this.setState({groupByRole: groupByRole})
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
                        <div className="tile is-ancestor" style={{margin: 'auto'}}>
                            <div className="tile is-vertical is-8" style={{margin:'auto'}}>
                                <div className="tile">
                                    <div className="tile is-parent is-vertical">
                                        {this.buildTiles().map(elem => elem)}
                                    </div>
                                </div>
                            </div>
                        </div>
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

    buildTiles() {
        let arr = new Array();
        this.state.groupByRole.forEach((v, k, m) =>
            arr.push(
                <article className="tile is-child notification is-dark">
                    <p className="title">{k}</p>
                    <p className="subtitle">{v}</p>
                </article>
            )
        );
        return arr;
    }
}

export default Dashboard;