
import type {ErrorResponse, RegisterRequest} from "../types.ts";
import {authService} from "../api/auth.service.ts";

export function renderRegister(appRoute: HTMLDivElement, navigate: () => void, toLogin: () => void): void {
    appRoute.innerHTML = `
        <div class="auth-container">
            <p>Sign up to Library</p>
             
             <form id="registerForm">
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
                <button type="submit">Register</button>
             </form>
             <p>Already have an account? <a href="#" id="toLogin">Sign in</a></p>
        </div>
    `;

    document.getElementById('toLogin')?.addEventListener('click', (e) => {
        e.preventDefault();
        toLogin();
    });

    document.getElementById('registerForm')?.addEventListener('submit', async (e) => {
        e.preventDefault();

        appRoute.querySelectorAll('.error-message').forEach(span => span.textContent = '');

        const usernameInput = document.getElementById('username') as HTMLInputElement;
        const passwordInput = document.getElementById('password') as HTMLInputElement;

        const request: RegisterRequest = {
            username: usernameInput.value,
            password: passwordInput.value
        };

        try {
            await authService.register(request);
            navigate();
        } catch (err) {
            const error = err as ErrorResponse;
            if (error.errors) {
                Object.keys(error.errors).forEach((fieldName: string) => {
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