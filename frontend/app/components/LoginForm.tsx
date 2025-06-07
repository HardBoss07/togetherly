"use client";

import React, {useState} from "react";
import InputField from "../components/InputField";
import {useRouter} from "next/navigation";

export default function LoginForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const router = useRouter();

    const ENV_API = process.env.NEXT_PUBLIC_API_URL;

    async function handleSubmit(e: React.FormEvent) {
        console.log("Form submitted with:", {username, password});

        e.preventDefault();

        console.log("Form submitted with after preventDefault:", {username, password});

        try {
            const res = await fetch(`${ENV_API}/api/auth/login`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({username, password}),
            });

            if (res.ok) {
                const data = await res.json();
                localStorage.setItem("token", data.token);
                router.push("/profile");
            } else {
                const error = await res.json();
                alert("Login failed: " + (error.message || "Unknown error"));
            }
        } catch (err) {
            console.error("Login error:", err);
            alert("Something went wrong");
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