import {useState} from "react";
import {AuthService} from "../api/auth.service.ts";
import * as React from "react";
import type {LoginRequest} from "../types";
import {useNavigate} from "react-router-dom";

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault();

        const request: LoginRequest = {
            username: username,
            password: password
        };

        try {
            await AuthService.login(request);
            navigate("/");
        } catch (err) {
            console.error(err);
            alert("Invalid email or password");
        }
    }

    const handleGoogle = () => {
        console.log("Google");
    }

    const handleGithub = () => {
        console.log("Github");
    }

    const handleSignUpBtn = () => {
        navigate("/register");
    }

    const handleResetPasswordBtn = () => {
        console.log("Reset password clicked");
        navigate("/reset-password");
    }

    return (
        <div className="h-screen flex justify-center items-center p-5 transition-all duration-200 bg-slate-200">
            <div className="max-w-xl w-full p-5 md:p-10 bg-white rounded-xl shadow-lg shadow-slate-300 flex flex-col gap-2">
                <div className="flex flex-col items-center justify-center gap-2">
                    <p className="text-3xl text-blue-700 font-semibold text-center">Welcome back!</p>
                    <p className="text-slate-700 text-center">Log in to your account</p>
                </div>

                <form id="loginForm"
                      onSubmit={handleSubmit}
                      className="flex flex-col gap-5">
                    <div className="flex flex-col gap-1 group">
                        <label htmlFor="username" className="text-slate-400 group-has-hover:text-blue-600 group-has-focus:text-blue-700 transition-all duration-200">Username:</label>
                        <input type="text"
                               id="username"
                               placeholder="john.doe"
                               onChange={(e) => setUsername(e.target.value)}
                               className="px-5 py-3 rounded-lg border border-slate-400 focus:outline-none focus:ring-0 hover:border-blue-600 focus:border-blue-700 transition-all duration-200"
                               required/>
                    </div>

                    <div className="flex flex-col gap-1 group">
                        <label htmlFor="password" className="text-slate-400 group-has-hover:text-blue-600 group-has-focus:text-blue-700 transition-all duration-200">Password:</label>
                        <input type="password"
                               id="password"
                               placeholder="******"
                               onChange={(e) => setPassword(e.target.value)}
                               className="px-5 py-3 rounded-lg border border-slate-400 focus:outline-none focus:ring-0 hover:border-blue-600 focus:border-blue-700 transition-all duration-200"
                               required/>
                    </div>

                    <div className="flex">
                        <button type="submit"
                                className="px-5 py-3 flex-1 text-white rounded-lg bg-blue-600 hover:bg-blue-700 active:bg-blue-800 transition-all duration-200">Login</button>
                    </div>
                </form>

                <div className="flex flex-row items-center justify-between gap-2">
                    <button id="reset-pass-btn"
                            onClick={handleResetPasswordBtn}
                            className="text-blue-500 active:text-blue-700 cursor-pointer">Reset password</button>
                    <button id="sign-up-btn"
                            onClick={handleSignUpBtn}
                            className="text-blue-500 active:text-blue-700 cursor-pointer">Sign up</button>
                </div>

                <div className="flex flex-col gap-1 items-center">
                    <p className="text-slate-700">Or you can sign in with</p>
                    <div className="flex flex-row items-center justify-center gap-1">
                        <button id="sign-with-google"
                                onClick={handleGoogle}
                                className="px-4 py-1 rounded-lg bg-slate-300">Google</button>
                        <button id="sign-with-github"
                                onClick={handleGithub}
                                className="px-4 py-1 rounded-lg bg-black text-white">Github</button>
                    </div>
                </div>
            </div>
        </div>
    );
}