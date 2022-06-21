import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import SessionUtil from 'src/utils/session.util';
import ToastrUtil from 'src/utils/toastr.util';

@Injectable({
    providedIn: 'root'
})
export class PublicGuard implements CanActivate {
    isSession = this.sessionUtil.sessionExist();
    onboardingStages = ['', '/address', '/mobile-verification']
    constructor(
        private sessionUtil: SessionUtil,
        private router: Router,
        private toastr: ToastrUtil
    ) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        const sessionData = this.sessionUtil.getSession();
        if (this.isSession) {
            if (sessionData?.userType == 'user') {
                if (sessionData.onboardingStage >= 0 && sessionData.onboardingStage <= 2) {
                    const desiredUrl = "/onboarding" + this.onboardingStages[sessionData.onboardingStage];
                    this.router.navigateByUrl(desiredUrl);
                } else {
                    this.router.navigateByUrl("/user");
                }
            }
        }
        return true;
    }
}
