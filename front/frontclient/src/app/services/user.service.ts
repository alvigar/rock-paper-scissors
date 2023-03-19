import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../interfaces/User";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

@Injectable({
    providedIn: 'root',
  })
export class UserService {

    private gameUrl: string;

    constructor(private http: HttpClient) {
        this.gameUrl = 'http://localhost:8080/api/user'
    }

    listUsers(): Observable<User[]> {
        return this.http.get<User[]>(`${this.gameUrl}`, httpOptions)
    }
    
    enable(user: string): Observable<User> {
        return this.http.put<User>(`${this.gameUrl}/${user}/enable`, {}, httpOptions)
    }

    disable(user: string): Observable<User> {
        return this.http.put<User>(`${this.gameUrl}/${user}/disable`, {}, httpOptions)
    }

    modifyRoles(user: string, roles: ('USER' | 'ADMIN')[]): Observable<User> {
      return this.http.post<User>(`${this.gameUrl}/${user}/roles`, { roles }, httpOptions)
    }

    changePassword(user: string, password: string): Observable<User> {
      return this.http.post<User>(`${this.gameUrl}/${user}/password`, { password }, httpOptions)
    }

    delete(user: string): Observable<any> {
      return this.http.delete<any>(`${this.gameUrl}/${user}`, httpOptions)
    }
}