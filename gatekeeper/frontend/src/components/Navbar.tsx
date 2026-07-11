import {UserService} from "../api/user.service.ts";
import {useEffect, useState} from "react";
import type {ProfileResponse} from "../types";
import {useNavigate} from "react-router-dom";

export function Navbar() {
    const [profile, setProfile] = useState<ProfileResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        UserService.getProfile()
            .then((data) => setProfile(data))
            .catch((error) => console.error("Failed to fetch user: ", error))
            .finally(() => setLoading(false));
    }, []);

    const handleLogout = () => {
        console.log("Logout clicked...");
        localStorage.removeItem("accessToken");
        navigate("/login");
    }

    if (loading) {
        return (
            <div className="p-5 text-slate-500">
                Fetching profile information...
            </div>
        )
    }

    return (
        <div className="flex items-center justify-between p-5 w-full border-b border-slate-200">
            <div className="flex flex-row gap-2">
                <p className="text-2xl text-blue-600 font-semibold">Gatekeeper</p>
            </div>

            <div className="flex flex-row gap-4 items-center">
                <p className="text-slate-700">
                    Welcome, <span className="text-black font-semibold">{profile?.username || "Guest"}</span>!
                </p>
                <button onClick={handleLogout}
                        className="px-3 py-1 rounded-lg text-white bg-blue-600 hover:bg-blue-700 active:bg-blue-800 transition-all duration-200">Logout</button>
            </div>
        </div>
    );
}