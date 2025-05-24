"use client";

import React, {useState} from "react";
import InputField from "../components/InputField";

export default function LoginForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const ENV_API = process.env.REACT_APP_ENV_API_URL;

    async function handleSubmit(e: React.FormEvent) {
        e.preventDefault();

        const res = await fetch(`${ENV_API}/api/auth/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({username, password}),
        });

        if (res.ok) {
            alert("Logged in!");
        } else {
            alert("Login failed");
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <InputField
                label="Username"
                name="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <InputField
                label="Password"
                name="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit">Login</button>
        </form>
    );
}