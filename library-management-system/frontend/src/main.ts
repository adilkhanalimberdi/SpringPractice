import './style/input.css';
import {renderLogin} from "./pages/login.ts";
import {tokenRepository} from './api/token.repository.ts';
import {renderDashboard} from "./pages/dashboard.ts";
import {renderRegister} from "./pages/register.ts";

const appRoute = document.getElementById('app') as HTMLDivElement;

export function router(): void {
    const token = tokenRepository.getAccessToken();

    if (!token) {
        renderLogin(
            appRoute,
            () => router(),
            () => displayRegister()
        );
    } else {
        renderDashboard(appRoute, () => router());
    }
}

function displayRegister() {
    return renderRegister(
        appRoute,
        () => router(),
        () => router()
    );
}

router();