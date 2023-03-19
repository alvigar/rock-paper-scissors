import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs';
import { UserDataSource } from 'src/app/helpers/userDataSource';
import { User } from 'src/app/interfaces/User';
import { UserService } from 'src/app/services/user.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent {
  message?: string;
  showDialog = false;
  password = '';
  isError = false;

  definedRoles = ['USER', 'ADMIN']

  displayedColumns: string[] = ['user', 'roles', 'enable', 'reset-password', 'delete']
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  dataSource: UserDataSource;



  ngAfterViewInit() {
    this.paginator?.page.pipe(tap(() => this.loadGamePage())).subscribe()
  }

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, public dialog: MatDialog) {
    this.route.queryParams.subscribe(params => {
      const message = params['message'];
      if (message) {
        this.isError = params['error'] ? true : false
        this.message = message;
        this.showDialog = true;
        this.closeDialog();
      }
    });
    this.dataSource = new UserDataSource(this.userService);

  }

  ngOnInit(): void {
    this.dataSource.loadUsers()
  }

  closeDialog() {
    setTimeout(() => {
      this.showDialog = false;
      this.message = undefined;
    }, 3000);
  }

  private loadGamePage() {
    this.dataSource.loadUsers(undefined, undefined, undefined, this.paginator?.pageIndex, this.paginator?.pageSize)
  }

  modifyRoles(user: User) {
    const roles: ("USER" | "ADMIN")[] = []
    if (user.roleADMIN) roles.push("ADMIN")
    if (user.roleUSER) roles.push("USER")
    this.userService.modifyRoles(user.nickname as string, roles).subscribe({
      next: _ => {
        this.router.navigate(['/configuration'], { queryParams: { message: `Roles of ${user.nickname} modified correctly` } })
      },
      error: _ => {
        this.router.navigate(['/configuration'], { queryParams: { message: `Error modifying roles for ${user.nickname}`, error: true } })
      }
    })
  }

  enableDisable(user: User) {
    if (user.enabled === true) {
      this.userService.enable(user.nickname as string).subscribe({
        next: _ => {
          this.router.navigate(['/configuration'], { queryParams: { message: `${user.nickname} enabled correctly` } })
        },
        error: _ => {
          this.router.navigate(['/configuration'], { queryParams: { message: `Error enabling ${user.nickname}`, error: true } })
        }
      })
    } else {
      this.userService.disable(user.nickname as string).subscribe({
        next: _ => {
          this.router.navigate(['/configuration'], { queryParams: { message: `${user.nickname} disabled correctly` } })
        },
        error: error => {
          this.router.navigate(['/configuration'], { queryParams: { message: `Error disabling ${user.nickname}`, error: true } })
        }
      })
    }
  }

  onSubmitChangePassword(user: User) {
    this.userService.changePassword(user.nickname as string, user.password as string).subscribe({
      next: _ => {
        this.router.navigate(['/configuration'], { queryParams: { message: `Password changed for ${user.nickname}` } })
      },
      error: error => {
        this.router.navigate(['/configuration'], { queryParams: { message: `Error changing password for ${user.nickname}`, error: true } })
      }
    })
  }

  reloadPage(): void {
    window.location.reload();
  }

  delete(user: User) {
    this.userService.delete(user.nickname as string).subscribe({
      next: _ => {
        this.router.navigate(['/configuration'], { queryParams: { message: `User ${user.nickname} deleted` } })
      },
      error: _ => {
        this.router.navigate(['/configuration'], { queryParams: { message: `Error deleting ${user.nickname}`, error: true } })
      }
    })
  }

  openDialog(user: User): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, { data: { user } });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'YES') {
        this.delete(user)
        this.reloadPage()
      }
    });
  }
}
