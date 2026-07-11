import type {ProfileResponse} from "../types";
import {api} from "./api.ts";

api.interceptors.request.use((config) => {
    const token = localStorage.getItem("accessToken");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
})

export const UserService = {
    async getProfile(): Promise<ProfileResponse> {
        const response = await api.get("/users/profile");
        return response.data;
    }
}