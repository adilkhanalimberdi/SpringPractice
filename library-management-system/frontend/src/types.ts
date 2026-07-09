export interface AuthResponse {
    token: string
}

export interface ErrorResponse {
    statusCode: number,
    message: string,
    errors?: Record<string, string>
}

export interface LoginRequest {
    username: string,
    password: string,
}

export interface RegisterRequest {
    username: string,
    password: string,
}