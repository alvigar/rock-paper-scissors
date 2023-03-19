import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from "../services/storage.service";



@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

    constructor(private storageService: StorageService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = this.storageService.getUser()?.token
        if (token) {
            const cloned = req.clone({
                withCredentials: true,
                headers: req.headers.set('Authorization', `Bearer ${token}`)
            })
            return next.handle(cloned)
        }

        return next.handle(req)
    }
}

export const httpInterceptorProviders = [
    { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true }
]