import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import SessionUtil from './session.util';


@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
    baseApiUrl = environment.baseApiUrl;
    token!: string;
    isSession!: boolean;
    constructor(private sessionUtil: SessionUtil) {
        this.token = sessionUtil.getSession()?.token;
        this.isSession = sessionUtil.sessionExist();
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (!req.url.startsWith(this.baseApiUrl + "/auth") && this.isSession) {
            req = req.clone({
                setHeaders: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Credentials': 'true',
                    'Access-Control-Allow-Origin': '*',
                    'Access-Control-Allow-Methods': 'GET, POST, PATCH, DELETE, PUT, OPTIONS',
                    'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers',
                    'Authorization': `Bearer ${this.token}`,
                },
            });
        }
        return next.handle(req);
    }
}