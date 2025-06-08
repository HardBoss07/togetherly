"use client";

import {useEffect, useState} from "react";
import {decodeJwt} from "@/util/decodeJwt";

interface Team {
    id: number;
}

export default function UserTeams({token}: {token: string}) {
    const [teams, setTeams] = useState<Team[]>([]);
    const ENV_API = process.env.NEXT_PUBLIC_API_URL;

    useEffect(() => {
        if (!token) return;

        fetch(`${ENV_API}/api/teams/personal`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => res.json())
            .then(setTeams)
            .catch((e) => console.error("Failed to fetch teams:", e));
    }, [token, ENV_API]);

    return (
      <ul>
          {teams.map((team) => (
              <li key={team.id}>Team ID: {team.id}</li>
          ))}
      </ul>
    );
}