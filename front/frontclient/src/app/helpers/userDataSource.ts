import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { BehaviorSubject, Observable, catchError, finalize, of } from "rxjs";
import { FIGURE } from "../figure";
import { User } from "../interfaces/User";
import { UserService } from "../services/user.service";

export class UserDataSource implements DataSource<User> {

    private userSubject = new BehaviorSubject<User[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    public length = 100;

    constructor(private userService: UserService) { }

    connect(collectionViewer: CollectionViewer): Observable<readonly User[]> {
        return this.userSubject.asObservable();
    }
    disconnect(collectionViewer: CollectionViewer): void {
        this.userSubject.complete();
        this.loadingSubject.complete();
    }

    loadUsers(userId?: number, filter = '', sortDirection = 'asc', pageIndex = 0, pageSize = 20) {
        this.loadingSubject.next(true);

        this.userService.listUsers().pipe(catchError(() => of([])), finalize(() => this.loadingSubject.next(false))).subscribe(users => {
            const newUsers = users.map(user => {
                const rolesString = user.roles?.map(role => role.roleName)
                user.roleUSER = rolesString?.includes("USER")
                user.roleADMIN = rolesString?.includes("ADMIN")
                user.password = ''
                return user
            })
            this.length = newUsers.length;
            this.userSubject.next(newUsers.slice(pageIndex * pageSize, (pageIndex + 1) * pageSize))
        })
    }
}