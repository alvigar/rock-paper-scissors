import { Component } from '@angular/core';
import { StorageService } from './services/storage.service';
import { AuthService } from './services/auth.service';
import { GameService } from './services/game.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Rock, Paper, Scissors!!';
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  username?: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private storageService: StorageService, 
    private gameService: GameService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user?.roles as string[];

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');

      this.username = user?.username;
    }
  }

  newGame() {
    this.gameService.newGame().subscribe({
      next: result => this.gotoNewGame(result.id as number),
      error: err => console.error(err)
    })
  }

  gotoNewGame(id: number) {
    this.router.navigate(['/play'], { queryParams: { game_id: id } })
  }

  logout(): void {
    this.storageService.clean();
    window.location.reload();
  }
}
