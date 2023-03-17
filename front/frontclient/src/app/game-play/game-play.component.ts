import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../game.service';
import { FIGURE } from '../figure';

@Component({
  selector: 'app-game-play',
  templateUrl: './game-play.component.html',
  styleUrls: ['./game-play.component.css']
})
export class GamePlayComponent {

  game_id?: number;
  message?: string;
  showDialog = false;

  constructor(private route: ActivatedRoute, private router: Router, private gameService: GameService) {
    this.route.queryParams.subscribe(params => {
      this.game_id = params['game_id']
      const message = params['message'];
      if (message) {
        this.message = message;
        this.showDialog = true;
      }
    });
  }

  makeMovement(movement: FIGURE) {
    this.gameService.play(this.game_id as number, movement).subscribe(game => {
      if (game.winnerPlayer) {
        this.router.navigate([''], { queryParams: { message: game.winnerPlayer === 'PLAYER' ? 'Congrats! You win!' : 'Sorry... Best luck next time...' } })
      } else {
        this.router.navigate(['/play'], {queryParams: {game_id: this.game_id, message: `Ops! The machine also plays ${movement}` }})
      }
    })
  }
}
