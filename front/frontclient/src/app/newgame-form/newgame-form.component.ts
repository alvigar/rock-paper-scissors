import { Component, Input } from '@angular/core';
import { Game } from '../game';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../game.service';
import { User } from '../interfaces/User';

@Component({
  selector: 'app-newgame-form',
  templateUrl: './newgame-form.component.html',
  styleUrls: ['./newgame-form.component.css']
})
export class NewGameFormComponent {
  @Input() user: User;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService
  ) {
    this.user = new User()
  }

  onSubmit() {
    this.gameService.newGame(this.user as User).subscribe(result => this.gotoNewGame(result.id as number))
  }

  gotoNewGame(id: number) {
    this.router.navigate(['/play'], { queryParams: { game_id: id }})
  }
}
