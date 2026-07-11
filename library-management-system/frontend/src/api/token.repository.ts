export const tokenRepository = {
    setTokens: (accessToken: string, refreshToken: string): void => {
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
    },

    removeTokens: (): void => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
    },

    getAccessToken: (): string | null => localStorage.getItem('accessToken'),
    getRefreshToken: (): string | null => localStorage.getItem('refreshToken'),
}