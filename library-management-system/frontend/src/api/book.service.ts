import type {BookResponse} from "../types.ts";
import {authenticatedFetch} from "./apiUtils.ts";

const BASE_URL = 'http://localhost:8080/api/v1/books';

export const bookService = {
    async getAllBooks(): Promise<BookResponse[]> {
        const response = await authenticatedFetch(`${BASE_URL}`, {
            method: 'GET'
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Failed to fetch books");
        }

        return await response.json();
    }
}