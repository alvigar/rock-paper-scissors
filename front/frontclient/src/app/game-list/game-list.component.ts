import { Component, AfterViewInit, ViewChild, OnInit } from '@angular/core';
import { Game } from '../game';
import { GameService } from '../game.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FIGURE } from '../figure';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { GameDataSource } from '../gameDataSource';
import { tap } from 'rxjs';


@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements AfterViewInit, OnInit {
  message?: string;
  showDialog = false;

  displayedColumns: string[] = ['id', 'user', 'winner', 'movements', 'play']
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  dataSource: GameDataSource;



  ngAfterViewInit() {
    this.paginator?.page.pipe(tap(() => this.loadGamePage())).subscribe()
  }

  constructor(private route: ActivatedRoute, private router: Router, private gameService: GameService) {
    this.route.queryParams.subscribe(params => {
      const message = params['message'];
      if (message) {
        this.message = message;
        this.showDialog = true;
        this.closeDialog();
      }
    });
    this.dataSource = new GameDataSource(this.gameService);
  }

  ngOnInit(): void {
    this.dataSource.loadGames();
  }

  closeDialog() {
    setTimeout(() => {
      this.showDialog = false;
      this.message = undefined;
    }, 3000);
  }

  private loadGamePage() {
    this.dataSource.loadGames(undefined, undefined, undefined, this.paginator?.pageIndex, this.paginator?.pageSize)
  }
}
