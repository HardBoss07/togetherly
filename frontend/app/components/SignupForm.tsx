"use client";

import React, { useState } from "react";
import InputField from "./InputField";

export default function SignupForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [repeatPassword, setRepeatPassword] = useState("");

    const ENV_API = process.env.NEXT_PUBLIC_API_URL;

    async function handleSubmit(e: React.FormEvent) {
        e.preventDefault();

        if (password !== repeatPassword) {
            alert("Passwords do not match");
            return;
        }

        const res = await fetch(`${ENV_API}/api/auth/signup`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password }),
        });

        if (res.ok) {
            alert("Signed up successfully!");
        } else {
            const msg = await res.text();
            alert("Signup failed: " + msg);
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
            <InputField
                label="Repeat Password"
                name="repeatPassword"
                type="password"
                value={repeatPassword}
                onChange={(e) => setRepeatPassword(e.target.value)}
            />
            <button type="submit">Sign Up</button>
        </form>
    );
}