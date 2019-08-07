import React from "react";
import {createUser} from "../auth/UserApi";

const inputLoginCss = {
    marginBottom: '2px'
}

const buttonLoginCss = {
    float: 'right'
}

class CreateUser extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            role: ''
        };

        this.handleChangeForUsername = this.handleChangeForUsername.bind(this);
        this.handleChangeForPassword = this.handleChangeForPassword.bind(this);
        this.handleChangeForRole = this.handleChangeForRole.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChangeForUsername(event) {
        this.setState({username: event.target.value});
    }

    handleChangeForPassword(event) {
        this.setState({password: event.target.value});
    }

    handleChangeForRole(event) {
        this.setState({role: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        let body = {
            username: this.state.username,
            password: this.state.password,
            role: this.state.role,
            app: this.props.app
        };

        createUser(body, this.props.token)
            .then(() => this.props.updateUsersList());
    }

    render() {
        return (
            <div className="card" style={{padding: '5px', width: '90%', margin: 'auto'}}>
                <header className="card-header">
                    <p className="card-header-title">
                        Create User
                    </p>
                </header>
                <form onSubmit={this.handleSubmit}>
                    <input className="input"
                           type="text"
                           placeholder="Username"
                           style={inputLoginCss}
                           value={this.state.username}
                           onChange={this.handleChangeForUsername}/>
                    <input
                        className="input"
                        type="password"
                        placeholder="password"
                        style={inputLoginCss}
                        value={this.state.password}
                        onChange={this.handleChangeForPassword}/>
                    <input
                        className="input"
                        type="text"
                        placeholder="role"
                        style={inputLoginCss}
                        value={this.state.role}
                        onChange={this.handleChangeForRole}/>
                    <input
                        className="button"
                        type="submit"
                        value="Login"
                        // style={buttonLoginCss}
                    />
                </form>
            </div>)
    }
}

export default CreateUser;