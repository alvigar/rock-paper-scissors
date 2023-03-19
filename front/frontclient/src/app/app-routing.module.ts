import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GamePlayComponent } from './components/game-play/game-play.component';
import { NewGameFormComponent } from './components/newgame-form/newgame-form.component';
import { GameListComponent } from './components/game-list/game-list.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';

const routes: Routes = [
  { path: 'newgame', component: NewGameFormComponent },
  { path: '', component: GameListComponent },
  { path: 'play', component: GamePlayComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'profile', component: ProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
