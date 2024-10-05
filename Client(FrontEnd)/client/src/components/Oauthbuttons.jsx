import React from 'react';

function Oauthbuttons() {
    const googleSignIn = () => window.open('/api/auth/google', '_self');
    const facebookSignIn = () => window.open('/api/auth/facebook', '_self');

    return (
        <div className="oauthbuttons">
            <button onClick={googleSignIn} className="btn btn-danger">Sign in with Google</button>
            <button onClick={facebookSignIn} className="btn btn-primary">Sign in with Facebook</button>
        </div>
    );
}

export default Oauthbuttons;
