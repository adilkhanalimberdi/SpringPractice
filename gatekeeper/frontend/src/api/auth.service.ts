import {api} from "./api.ts";
import type {LoginRequest} from "../types";

export const AuthService = {
    async login(request: LoginRequest): Promise<void> {
        const response = await api.post("/auth/login", request);

        if (response.data && response.data.token) {
            localStorage.setItem("accessToken", response.data.token);
        }
    }
}