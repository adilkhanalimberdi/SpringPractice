import {tokenRepository} from "./token.repository.ts";
import {authService} from "./auth.service.ts";

export async function authenticatedFetch(url: string, options: RequestInit = {}): Promise<Response> {
    const headers = {
        ...options.headers,
        'Authorization': `Bearer ${tokenRepository.getAccessToken()}`,
    }

    let response = await fetch(url, { ...options, headers });

    if (response.status === 401) {
        try {
            await authService.refreshToken();

            const newHeaders = {
                ...options.headers,
                'Authorization': `Bearer ${tokenRepository.getAccessToken()}`,
            }

            response = await fetch(url, { ...options, headers: newHeaders });
        } catch (refreshError) {
            console.error("Refresh token expired or invalid. Logging out...");
            await authService.logout();
            throw refreshError;
        }
    }

    return response;
}