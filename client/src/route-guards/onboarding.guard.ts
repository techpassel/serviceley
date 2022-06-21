import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import SessionUtil from 'src/utils/session.util';
import ToastrUtil from 'src/utils/toastr.util';

@Injectable({
  providedIn: 'root'
})
export class OnboardingGuard implements CanActivate {
  isSession = this.sessionUtil.sessionExist();
  onboardingStages = ['/onboarding', '/onboarding/address', '/onboarding/mobile-verification']
  constructor(
    private sessionUtil: SessionUtil,
    private router: Router,
    private toastr: ToastrUtil
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (!this.isSession) {
      this.toastr.showWarning("Please login first to access onboarding pages.")
      this.router.navigateByUrl("/auth/login");
    }

    const sessionData = this.sessionUtil.getSession();
    if (sessionData && sessionData.onboardingStage > 2) {
      this.router.navigateByUrl("/user")
    } else if (sessionData.onboardingStage >= 0 && sessionData.onboardingStage <= 2) {
      const desiredUrl = this.onboardingStages[sessionData.onboardingStage];
      const currentUrlIndex = this.onboardingStages.findIndex(e => e === state.url)
      if (currentUrlIndex > sessionData.onboardingStage) {
        //Since we want to allow user to move to next onboarding stage only if he/she has completed previous step.
        this.router.navigateByUrl(desiredUrl);
      }
    } else {
      this.router.navigateByUrl("")
    }
    return true;
  }
}
