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
            role: '',
            hasError: false,
            errorMessage: '',
            isSuccess: false
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
            .then(() => {
                this.props.updateUsersList();
                this.setState({hasError: false, isSuccess: true})
            }).catch(error => {
            this.setState({hasError: true, errorMessage: error.response.data.errorMessage, isSuccess: false})
        });
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
                        value="Create"
                    />
                </form>
                {this.state.hasError ?
                    <article className="message is-danger">
                        <div className="message-body">
                            {this.state.errorMessage}
                        </div>
                    </article> : null
                }
                {this.state.isSuccess ?
                    <article className="message is-success">
                        <div className="message-body">
                            User created successfully.
                        </div>
                    </article> : null
                }
            </div>)
    }
}

export default CreateUser;