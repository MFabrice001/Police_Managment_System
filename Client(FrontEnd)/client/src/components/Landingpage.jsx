import React from 'react';
import { Link } from 'react-router-dom';
import './styles/landing.css';

function Landingpage() {
    return (
        <div className="landingpage container">
            <h1>Welcome to the Police Management System</h1>
            <p>Manage law enforcement activities easily.</p>
            <Link to="/signin" className="btn btn-primary">Sign In</Link>
            <Link to="/signup" className="btn btn-secondary">Sign Up</Link>
        </div>
    );
}

export default Landingpage;
