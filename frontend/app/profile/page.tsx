"use client";

import {useEffect, useState} from "react";
import {decodeJwt, JwtPayload} from "@/util/decodeJwt";
import UserTeams from "@/app/components/UserTeams";

export default function ProfilePage() {
    const [username, setUsername] = useState<string | null>(null);
    const [token, setToken] = useState<string | null>(null);

    useEffect(() => {
        const storedToken = localStorage.getItem("token");
        setToken(storedToken);

        if (storedToken) {
            const decoded = decodeJwt(storedToken);
            if (decoded) {
                setUsername(decoded.sub);
            }
        }
    }, []);

    if (!token) {
        return <p>No token found. Please log in.</p>;
    }

    return (
        <div>
            <h1>Profile Page</h1>
            <p><strong>Username:</strong> {username}</p>
            <p><strong>JWT Token:</strong> {token}</p>
            <p><strong>Teams:</strong></p>
            <UserTeams token={token}/>
        </div>
    );
}
