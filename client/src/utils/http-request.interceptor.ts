import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CommonService } from 'src/services/common/common.service';
import { SessionObservableService } from 'src/services/observables-related/session-observable.service';
import SessionUtil from './session.util';
import ToastrUtil from './toastr.util';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
    baseApiUrl = environment.baseApiUrl;
    token!: string | null;
    isSession!: boolean;
    constructor(
        private sessionUtil: SessionUtil,
        private commonService: CommonService,
        private toastr: ToastrUtil,
        private router: Router,
        private sessionObservableService: SessionObservableService
    ) {
        this.sessionObservableService.sessionEventListener().subscribe(info => {
            this.isSession = info;
            if (this.isSession) {
                this.token = this.sessionUtil.getSession()?.token;
            } else {
                if(this.token){
                    this.token = null;
                }
            }
        })
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
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                let errorObj: any = {};
                if (error.error instanceof ErrorEvent) {
                    errorObj['message'] = error.error.message;
                } else {
                    errorObj = error;
                    if (error.status == 403 || error.status == 401) {
                        this.commonService.logout();
                        this.toastr.showError("Token expired. Please login again.");
                        this.router.navigateByUrl("/auth/login");
                    }
                }
                return throwError(() => errorObj);
            })
        )
    }
}