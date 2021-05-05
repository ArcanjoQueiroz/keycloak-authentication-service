import React, {Component} from 'react';
import Keycloak from 'keycloak-js';

export default class Secured extends Component {
  constructor(props) {
    super(props);
    this.state = {keycloak: null, authenticated: false};
  }

  componentDidMount() {
    const keycloak = Keycloak({
      url: '/auth',
      realm: 'test',
      clientId: 'test',
    });
    keycloak.init({
      flow: 'implicit',
      onLoad: 'login-required',
    }).then((authenticated) => {
      this.setState({keycloak: keycloak, authenticated: authenticated});
      if (authenticated) {
        window.accessToken = keycloak.token;
      }
    });
  }

  render() {
    if (this.state.keycloak) {
      if (this.state.authenticated) {
        return (<span>{this.props.children}</span>);
      }
      return (
        <div>Unable to authenticate!</div>
      );
    }
    return (
      <div>Initialising Keycloak...</div>
    );
  }
}
