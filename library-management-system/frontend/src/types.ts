export interface AuthResponse {
    accessToken: string,
    refreshToken: string,
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

export interface RefreshRequest {
    token: string,
}

export interface Author {
    id: string,
    name: string,
}

export interface BookResponse {
    id: string,
    title: string,
    author: Author,
    publishedYear: number,
    isbn: string,
    createdAt: Date,
    updatedAt: Date,
}