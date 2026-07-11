import type {AuthResponse, ErrorResponse, LoginRequest, RefreshRequest, RegisterRequest} from "../types.ts";
import {tokenRepository} from "./token.repository.ts";

const BASE_URL = "http://localhost:8080/api/v1/auth";

export const authService = {
    async login(request: LoginRequest): Promise<void> {
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

        const result: AuthResponse = await response.json();
        tokenRepository.setTokens(result.accessToken, result.accessToken);
    },

    async register(request: RegisterRequest): Promise<void> {
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

        const result: AuthResponse = await response.json();
        tokenRepository.setTokens(result.accessToken, result.accessToken);
    },

    async logout(): Promise<void> {
        try {
            await fetch(`${BASE_URL}/logout`, {
                method: "POST"
            });
        } catch (error) {
            console.error("Error logging out: " + error);
        } finally {
            tokenRepository.removeTokens();
        }
    },

    async refreshToken(): Promise<void> {
        const refreshToken = tokenRepository.getRefreshToken();

        if (refreshToken === null) {
            return;
        }

        console.log("refreshing the tokens...");

        const request: RefreshRequest = {
            "token": refreshToken,
        };

        const response = await fetch(`${BASE_URL}/refresh`, {
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

        const result: AuthResponse = await response.json();
        tokenRepository.setTokens(result.accessToken, result.refreshToken);
    }
};