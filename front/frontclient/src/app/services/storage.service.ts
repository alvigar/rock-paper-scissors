import { Injectable } from '@angular/core';
import { UserDTO } from '../interfaces/UserDTO';

const USER_KEY = 'auth-user';

@Injectable({
    providedIn: 'root'
})
export class StorageService {
    constructor() { }

    clean(): void {
        window.localStorage.clear()
        window.localStorage.removeItem(USER_KEY)
    }

    public saveUser(user: UserDTO): void {
        window.localStorage.removeItem(USER_KEY)
        window.localStorage.setItem(USER_KEY, JSON.stringify(user))
    }

    public getUser(): UserDTO | undefined {
        const user = window.localStorage.getItem(USER_KEY)
        if (user) {
            return JSON.parse(user)
        }
        return undefined
    }

    public isLoggedIn(): boolean {
        const user = window.localStorage.getItem(USER_KEY)
        if (user) {
            return true
        }
        return false
    }
}