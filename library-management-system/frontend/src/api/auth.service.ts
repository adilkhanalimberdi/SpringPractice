import type {AuthResponse, ErrorResponse, LoginRequest, RegisterRequest} from "../types.ts";

const BASE_URL = "http://localhost:8080/api/v1/auth";

export const authService = {
    async login(request: LoginRequest): Promise<AuthResponse> {
        const response = await fetch(`${BASE_URL}/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(request),
        });

        if (!response.ok) {
            const errorData: ErrorResponse = await response.json();
            throw errorData;
        }

        return response.json();
    },

    async register(request: RegisterRequest): Promise<AuthResponse> {
        const response = await fetch(`${BASE_URL}/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(request),
        });

        if (!response.ok) {
            const errorData: ErrorResponse = await response.json();
            throw errorData;
        }

        return response.json();
    }
};