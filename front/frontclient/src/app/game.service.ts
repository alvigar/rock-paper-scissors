import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from './game';
import { FIGURE } from './figure';
import { User } from './interfaces/User';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private gameUrl: string;

  constructor(private http: HttpClient) { 
    this.gameUrl = 'http://localhost:8080/games'
  }

  public findAll(): Observable<Game[]> {
    return this.http.get<Game[]>(`${this.gameUrl}`);
  }

  public newGame(user: User): Observable<Game> {
    return this.http.post<Game>(`${this.gameUrl}/new`, { user: user.nickname })
  }

  public getGame(id: number): Observable<Game> {
    return this.http.get<Game>(`${this.gameUrl}/${id}`)
  }

  public play(id: number, figure: FIGURE): Observable<Game> {
    return this.http.put<Game>(`${this.gameUrl}/${id}/play/${figure}`, {})
  }
}
