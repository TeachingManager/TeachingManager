import React from 'react';
import './loginpage.css';
import { GoogleReCaptchaProvider } from 'react-google-recaptcha-v3';
import logo from '../../assets/TeachingManager.svg';
import SignIn from './SignIn';



export default function LoginPage() {
    return (
        <div>
            <GoogleReCaptchaProvider reCaptchaKey="6LdH9ygqAAAAAN6WdRyVPVTe7eoOS32DUCZXyZvK">
                <SignIn/>
            </GoogleReCaptchaProvider>
        </div>
    );
}
