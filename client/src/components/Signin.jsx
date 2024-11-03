import React, { useState } from 'react';
import axios from 'axios';
import Oauthbuttons from './Oauthbuttons';
import './styles/auth.css';

function Signin() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSignIn = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/auth/signin', { email, password });
            console.log(response.data);
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div className="signin container">
            <h2>Sign In</h2>
            <form onSubmit={handleSignIn}>
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
                <button type="submit" className="btn btn-primary">Sign In</button>
            </form>
            <Oauthbuttons />
        </div>
    );
}

export default Signin;
