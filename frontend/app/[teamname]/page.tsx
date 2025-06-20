'use client';

import {useEffect, useState} from 'react';
import {useParams} from 'next/navigation';
import {Team, Task} from '@/util/EntityInterfaces';

interface TeamWithTasks {
    team: Team;
    tasks: Task[];
}

export default function TeamPage() {
    const {teamname} = useParams();
    const [team, setTeam] = useState<Team>();
    const [tasks, setTasks] = useState<Task[]>([]);
    const [token, setToken] = useState<string | null>(null);

    const ENV_API = process.env.NEXT_PUBLIC_API_URL;

    useEffect(() => {
        const storedToken = localStorage.getItem('token');
        setToken(storedToken);
    }, []);

    useEffect(() => {
        if (!teamname || !token) return;

        const fetchTeamData = async () => {
            try {
                const res = await fetch(`${ENV_API}/api/teams/full/${encodeURIComponent(teamname as string)}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (!res.ok) {
                    throw new Error(`Failed to fetch team data: ${res.status}`);
                }

                const data: TeamWithTasks = await res.json();
                setTeam(data.team);
                setTasks(data.tasks);
            } catch (err) {
                console.error(err);
            }
        };

        fetchTeamData();
    }, [teamname, token]);

    if (!token) return <p>Please log in to view this page.</p>;
    if (!team) return <p>Loading team data...</p>;

    return (
        <div>
            <h1>{team.teamName}</h1>
            <p>Created at: {new Date(team.createdAt).toLocaleString()}</p>

            <h2>Tasks</h2>
            <ul>
                {tasks.map((task) => (
                    <li key={task.taskId}>{task.description}</li>
                ))}
            </ul>
        </div>
    );
}