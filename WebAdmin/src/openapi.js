export const base = 'http://localhost:7900'

export const methods = {
    GET: 'GET',
    POST: 'POST',
    PUT: 'PUT',
    DELETE: 'DELETE'
}

export function _parseJSON(response) {
    return response.text().then(function(text) {
      return text ? JSON.parse(text) : {}
    })
  }

export const routes = {
    ACCOUNT: 'accounts',
    USERS: 'accounts',
    BILLSOUTPUT: 'billsOutput',
    GETPAYMENT: 'paymentBill/getAllPaymentBill',
    PAYMENTBILL: 'paymentBill',
    REMOVEFEEDBACK:'feedback',
    BILLINPUT: 'billsInput',
    ORDERS: 'bills',
    UPDATEINPUTBILL: '/billsInput/updateBillStatus',
    UPDATEPROFILE: 'Account/bills/updateBillStatus/UpdateProfile',
    CHANGEPASSWORD: 'Account/ChangePassword',
    EVENTS: 'Events',
    EVENTTYPES: 'EventTypes',
    DELPRODUCTS: 'products',
    PRODUCTS: 'products',
    GETPRODUCTS: 'products/getAllProduct',
    ROLES: 'roles',
    CONFIGURATION: 'Configurations',
    SYSTEMRESOURCES: 'SystemResources'
}

export function getToken() {
    return localStorage.getItem('token');
}

export function setToken(data) {
    localStorage.setItem('token', data.id);
    localStorage.setItem('id', data.id);
    localStorage.setItem('username', data.username);
    localStorage.setItem('fullName', data.fullName);
    localStorage.setItem('email', data.email);
}

export function getUsername() {
    return localStorage.getItem('username');
}

export function getId() {
    return localStorage.getItem('id');
}

export function getAvatar() {
    let username = getUsername();
    return `img/avatars/${username}.jpg`;
}

export function clearToken() {
    return localStorage.removeItem('token');
}

export function requestToken(username, password) {
    const body = urlEncode({
        // grant_type: 'password',
        username,
        password
    });

    return fetch(`${base}/authencation/checkLogin`, {
        method: methods.POST,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body
    }).catch(catchNetworkError).then(res => 
        // res.json()
        _parseJSON(res)
        );
}

export function openapi(method, route, data, params) {
    let url = `${base}/${route}`;
    let body = null;
    if (data) {
        if (( method === methods.DELETE) && data.id) {
            url += `/${data.id}`;
        }
        Object.keys(data).forEach((key) => (data[key] == null) && delete data[key]);
        body = JSON.stringify(data);
    }
    if (params) {
        // url += `?${urlEncode(params)}`;
        url+= '/'+params;
    }
    let options = {
        method: method,
        headers: new Headers({
            // 'Authorization': 'Bearer ' + getToken(),
            'Content-Type': 'application/json'
        })
    };
    if (method !== methods.GET) {
        options.body = body;
    }
    return fetch(url, options).catch(catchNetworkError).then(res => {
        console.log(res.headers.get('Content-Type'))
        if (res.headers.get('Content-Type')) {
            return res.json();
        } else {
            return res.text();
        }
    });
}

// x-www-form-urlencoded
export const urlEncode = data => Object.keys(data)
    .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(data[key]))
    .join('&');

export const catchNetworkError = error => {
    alert('Network error. View console logs for more detail!')
    console.error(error);
}