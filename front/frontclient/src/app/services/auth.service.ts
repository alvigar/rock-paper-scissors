import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

@Injectable({
    providedIn: 'root',
  })
export class AuthService {

    private gameUrl: string;

    constructor(private http: HttpClient) {
        this.gameUrl = 'http://localhost:8080/api/auth'
    }

    login(user: string, password: string): Observable<any> {
        return this.http.post(`${this.gameUrl}/signin`, { user, password}, httpOptions)
    }

    register(user: string, password: string): Observable<any> {
        return this.http.post(`${this.gameUrl}/signup`, { user, password}, httpOptions)
    }

    logout(): Observable<any> {
        return this.http.post(`${this.gameUrl}/signout`, {}, httpOptions)
    }
}