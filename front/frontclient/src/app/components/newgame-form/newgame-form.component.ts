import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../../services/game.service';
import { User } from '../../interfaces/User';

@Component({
  selector: 'app-newgame-form',
  templateUrl: './newgame-form.component.html',
  styleUrls: ['./newgame-form.component.css']
})
export class NewGameFormComponent {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService
  ) {
  }

  onSubmit() {
    this.gameService.newGame().subscribe({
      next: result => this.gotoNewGame(result.id as number),
      error: err => console.error(err)
    })
  }

  gotoNewGame(id: number) {
    this.router.navigate(['/play'], { queryParams: { game_id: id } })
  }
}
