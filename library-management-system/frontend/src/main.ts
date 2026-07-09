import './style/input.css';
import type {ErrorResponse, LoginRequest, RegisterRequest} from "./types.ts";
import {authService} from "./api/auth.service.ts";

const appRoute = document.getElementById('app') as HTMLElement;

const getToken = (): string | null => localStorage.getItem('jwt_token');
const setToken = (token: string): void => localStorage.setItem('jwt_token', token);
const removeToken = (): void => localStorage.removeItem('jwt_token');

function router(): void {
    const token = getToken();

    if (!token) {
        renderLogin();
    } else {
        renderDashboard();
    }
}

function renderLogin(): void {
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
        renderRegister();
    });

    document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
        e.preventDefault();
        clearErrors();

        const usernameInput = document.getElementById('username') as HTMLInputElement;
        const passwordInput = document.getElementById('password') as HTMLInputElement;

        const request: LoginRequest = {
            username: usernameInput.value,
            password: passwordInput.value
        };

        try {
            const response = await authService.login(request);
            setToken(response.token);
            router();
        } catch (err) {
            handleAuthError(err as ErrorResponse);
        }
    });
}

function renderRegister(): void {
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
        renderLogin();
    });

    document.getElementById('registerForm')?.addEventListener('submit', async (e) => {
        e.preventDefault();
        clearErrors();

        const usernameInput = document.getElementById('username') as HTMLInputElement;
        const passwordInput = document.getElementById('password') as HTMLInputElement;

        const request: RegisterRequest = {
            username: usernameInput.value,
            password: passwordInput.value
        };

        try {
            const response = await authService.register(request);
            setToken(response.token);
            router();
        } catch (err) {
            handleAuthError(err as ErrorResponse);
        }
    });
}

function renderDashboard(): void {
    appRoute.innerHTML = `
        <div class="dashboard-container">
            <header>
                <p>Library Management System</p>
                <button id="logoutBtn">Sign out</button>
            </header>
            <main>
                <p>Welcome to the system!</p>
            </main>
        </div>
    `;

    document.getElementById('logoutBtn')?.addEventListener('click', () => {
        removeToken();
        router();
    });
}

function handleAuthError(err: ErrorResponse): void {
    if (err.errors) {
        Object.keys(err.errors).forEach((fieldName: string) => {
            const errorSpan = document.getElementById(`${fieldName}Error`);
            if (errorSpan && err.errors) {
                errorSpan.textContent = err.errors[fieldName];
            }
        });
    } else {
        alert(err.message || 'Something went wrong, please try later');
    }
}

function clearErrors(): void {
    document.querySelectorAll('.error-message').forEach((span) => {
        span.textContent = '';
    })
}

router();