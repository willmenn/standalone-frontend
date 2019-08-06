import React from "react";
import {authUser} from "../auth/AuthApi";
import axios from "axios";

const loginContainerCss = {
    width: '20%',
    top: '30%',
    left: '40%',
    position: 'absolute'
}

const inputLoginCss = {
    marginBottom: '2px'
}

const buttonLoginCss = {
    float: 'right'
}

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        };
        this.handleChangeForUsername = this.handleChangeForUsername.bind(this);
        this.handleChangeForPassword = this.handleChangeForPassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

    }

    handleChangeForUsername(event) {
        this.setState({username: event.target.value});
    }

    handleChangeForPassword(event) {
        this.setState({password: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        authUser(this.state.username, this.state.password)
            .then(res => {
                this.props.appHandler(res.data.appToken, 'DASHBOARD', res.data.token);
            });


    }

    render() {
        return (<div style={{background: '#F7F7F7'}}>
            <div className=" panel LoginContainer" style={loginContainerCss}>
                <section className="hero is-dark" style={inputLoginCss}>
                    <div className="hero-body">
                        <div className="container">
                            <h1 className="title">
                                Sign-In
                            </h1>
                        </div>
                    </div>
                </section>
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
                        className="button"
                        type="submit"
                        value="Login"
                        style={buttonLoginCss}/>
                </form>
            </div>
        </div>)
    }
}

export default Login;