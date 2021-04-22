import axios from 'axios';

const authServerBaseUrl = process.env.AUTH_SERVER_BASE_URL || 'http://localhost:9999/auth';
const realm =  process.env.REALM || 'master';
const clientId = process.env.CLIENT_ID || 'test';
const clientSecret = process.env.CLIENT_SECRET || '08d355bb-463d-4d18-b69e-a1ec1cb7ed11';
const username = process.env.USERNAME || 'alexandre';
const password = process.env.PASSWORD || 'foo';

const getAccessToken = async (clientId, clientSecret, username, password) => {
  const url = `${authServerBaseUrl}/realms/${realm}/protocol/openid-connect/token`;
  const form = `client_id=${clientId}&client_secret=${clientSecret}` +
   `&username=${username}&password=${password}&grant_type=password`;
  console.log(`Access Token Request URL: ${url}`);
  console.log(`Access Token Data: ${form}`);
  const res = await axios.post(url, form, {
    headers: {'Content-Type': 'application/x-www-form-urlencoded', 'Cache-Control': 'no-cache'},
  });
  return res.data;
};

const getUserInfo = async (accessToken) => {
  const url = `${authServerBaseUrl}/realms/${realm}/protocol/openid-connect/userinfo`;
  const authorization = `${accessToken.token_type} ${accessToken.access_token}`;
  console.log(`User Info Request URL: ${url}`);
  console.log(`User Info Authorization: ${authorization}`);
  const res = await axios.get(url, {
    headers: {'Authorization': authorization, 'Cache-Control': 'no-cache'},
  });
  return res.data;
};

const introspectAccessToken = async (clientId, clientSecret, accessToken) => {
  const url = `${authServerBaseUrl}/realms/${realm}/protocol/openid-connect/token/introspect`;
  const form = `client_id=${clientId}&client_secret=${clientSecret}` +
  `&token=${accessToken.access_token}`;
  console.log(`Introspect Request URL: ${url}`);
  console.log(`Introspect Data: ${form}`);
  const res = await axios.post(url, form, {
    headers: {'Content-Type': 'application/x-www-form-urlencoded', 'Cache-Control': 'no-cache'},
  });
  return res.data;
};

getAccessToken(clientId, clientSecret, username, password)
    .then((accessToken) => {
      introspectAccessToken(clientId, clientSecret, accessToken)
          .then( (i) => console.log('Introspect: ' + JSON.stringify(i)))
          .catch((e) => console.log(e));
      return accessToken;
    })
    .then((accessToken) => getUserInfo(accessToken))
    .then((userInfo) => console.log('UserInfo: ' + JSON.stringify(userInfo)))
    .catch((e) => console.log(e.message));
