import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Game } from "./game";
import { BehaviorSubject, Observable, catchError, finalize, of } from "rxjs";
import { GameService } from "./game.service";
import { FIGURE } from "./figure";

export class GameDataSource implements DataSource<Game> {

    private gameSubject = new BehaviorSubject<Game[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    public length = 100;

    constructor(private gameService: GameService) { }

    connect(collectionViewer: CollectionViewer): Observable<readonly Game[]> {
        return this.gameSubject.asObservable();
    }
    disconnect(collectionViewer: CollectionViewer): void {
        this.gameSubject.complete();
        this.loadingSubject.complete();
    }

    loadGames(gameId?: number, filter = '', sortDirection = 'asc', pageIndex = 0, pageSize = 5) {
        this.loadingSubject.next(true);

        this.gameService.findAll().pipe(catchError(() => of([])), finalize(() => this.loadingSubject.next(false))).subscribe(games => {
            const newGames = games.map(game => {
                const result: { player: FIGURE, machine: FIGURE }[] = []
                for (let i = 0; i < game.movements.length; i += 2) {
                    result.push({ player: game.movements[i].figure as FIGURE, machine: game.movements[i + 1].figure as FIGURE })
                }
                console.log(result)
                game.tuple = result;
                return game
            })
            this.length = newGames.length;
            this.gameSubject.next(newGames.slice(pageIndex * pageSize, (pageIndex + 1) * pageSize))
        })
    }
}