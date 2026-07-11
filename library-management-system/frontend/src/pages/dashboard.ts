import {bookService} from "../api/book.service.ts";
import type {BookResponse, ErrorResponse} from "../types.ts";
import {authService} from "../api/auth.service.ts";

export async function renderDashboard(appRoute: HTMLDivElement, navigate: () => void): Promise<void> {
    appRoute.innerHTML = `
        <div class="dashboard-container">
            <header>
                <p>Library Management System</p>
                <button id="logoutBtn">Sign out</button>
            </header>
            <main>
                <section>
                    <p>Books</p>
                    <div id="booksList">Loading...</div> 
                </section>
            </main>
        </div>
    `;

    const booksListContainer = document.getElementById('booksList') as HTMLDivElement;

    document.getElementById('logoutBtn')?.addEventListener('click', () => {
        authService.logout();
        navigate();
    });

    try {
        const books: BookResponse[] = await bookService.getAllBooks();
        if (books.length === 0) {
            booksListContainer.innerHTML = '<p>No available books yet.</p>';
        } else {
            booksListContainer.innerHTML = `
                <div>
                    ${books.map(book => (`
                        <div>
                            <p>${book.title}</p>
                            <p>${book.author.name}</p>
                        </div>
                    `))}  
                </div>
            `;
        }
    } catch (err) {
        const error = err as ErrorResponse;
        booksListContainer.innerHTML = `<p>Failed to load books: ${error.message || 'Access Denied'}</p>`;
    }
}