import type {ErrorResponse, LoginRequest} from "../types.ts";
import {authService} from "../api/auth.service.ts";

export function renderLogin(appRoute: HTMLDivElement, navigate: () => void, toRegister: () => void): void {
    appRoute.innerHTML = `
        <div class="auth-container">
            <p>Sign in to Library</p>
            <form id="loginForm">
                <div>
                    <label for="username">Username:</label>
                    <input type="text" id="username" required>
                    <span class="error-message" id="usernameError"></span>
                </div>
                <div>
                    <label for="password">Password:</label>
                    <input type="password" id="password" required>
                    <span class="error-message" id="passwordError"></span>
                </div>
                <button type="submit">Login</button>
            </form>
            <p>Don't have an account? <a href="#" id="toRegister">Sign up</a></p>
        </div>
    `;

    document.getElementById('toRegister')?.addEventListener('click', (e) => {
        e.preventDefault();
        toRegister();
    });

    document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
        e.preventDefault();

        appRoute.querySelectorAll('.error-message').forEach(span => span.textContent = '');

        const usernameInput = document.getElementById('username') as HTMLInputElement;
        const passwordInput = document.getElementById('password') as HTMLInputElement;

        const request: LoginRequest = {
            username: usernameInput.value,
            password: passwordInput.value
        };

        try {
            await authService.login(request);
            navigate();
        } catch (err) {
            const error = err as ErrorResponse;
            if (error.errors) {
                Object.keys(error.errors).forEach(fieldName => {
                    const errorSpan = document.getElementById(`${fieldName}Error`);
                    if (errorSpan && error.errors) {
                        errorSpan.textContent = error.errors[fieldName];
                    }
                });
            } else {
                alert(error.message || 'Something went wrong, please try later');
            }
        }
    });
}