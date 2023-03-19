import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from '../game';
import { FIGURE } from '../figure';
import { User } from '../interfaces/User';
import { StorageService } from './storage.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class GameService {

  private gameUrl: string;

  constructor(private http: HttpClient, private storageService: StorageService) {
    this.gameUrl = 'http://localhost:8080/api/game'
  }

  public findAll(): Observable<Game[]> {
    return this.http.get<Game[]>(`${this.gameUrl}`, httpOptions);
  }

  public newGame(): Observable<Game> {
    return this.http.post<Game>(`${this.gameUrl}/new`, { user: this.storageService.getUser()?.username }, httpOptions)
  }

  public getGame(id: number): Observable<Game> {
    return this.http.get<Game>(`${this.gameUrl}/${id}`, httpOptions)
  }

  public play(id: number, figure: FIGURE): Observable<Game> {
    return this.http.put<Game>(`${this.gameUrl}/${id}/play/${figure}`, {}, httpOptions)
  }
}
