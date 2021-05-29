import React, {useState, useEffect} from 'react';
import PropTypes from 'prop-types';
import Keycloak from 'keycloak-js';

const Secured = ({children}) => {
  const [keycloak, setKeycloak] = useState(null);
  const [authenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    const keycloak = Keycloak({
      url: '/auth',
      realm: 'test',
      clientId: 'test',
    });
    keycloak.init({
      flow: 'implicit',
      onLoad: 'login-required',
    }).then((authenticated) => {
      setKeycloak(keycloak);
      setAuthenticated(authenticated);
      if (authenticated) {
        window.accessToken = keycloak.token;
      }
    });
  }, []);

  if (keycloak) {
    if (authenticated) {
      return (<span>{children}</span>);
    }
    return (
      <div>Unable to authenticate!</div>
    );
  }
  return (
    <div>Initialising Keycloak...</div>
  );
};

Secured.propTypes = {
  children: PropTypes.any,
};

export default Secured;
