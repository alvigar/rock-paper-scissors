import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewGameFormComponent } from './newgame-form/newgame-form.component';
import { GameListComponent } from './game-list/game-list.component';
import { GamePlayComponent } from './game-play/game-play.component';

const routes: Routes = [
  { path: 'newgame', component: NewGameFormComponent },
  { path: '', component: GameListComponent },
  { path: 'play', component: GamePlayComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
