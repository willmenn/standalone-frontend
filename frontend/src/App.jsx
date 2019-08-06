import React from 'react';
import 'bulma'
import LoginPage from './page/Login'
import Dashboard from './page/Dashboard'

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            token: null,
            page: 'LOGIN',
            app: null
        }
        this.appHandler = this.appHandler.bind(this);
    }

    appHandler(app, newState, token) {
        this.setState({app: app, page: newState, token: token})
    }

    render() {
        switch (this.state.page) {
            case 'LOGIN':
                return <LoginPage
                    appHandler={this.appHandler}
                />
            case 'DASHBOARD':
                return <Dashboard
                    app={this.state.app}
                    token={this.state.token}/>
            default:
                return <LoginPage/>
        }
        ;
    }
};

export default App;
