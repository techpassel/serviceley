import { Injectable } from '@angular/core';
import SessionUtil from 'src/utils/session.util';
import { AuthService } from '../api-related/auth/auth.service';
import { SessionObservableService } from '../observables-related/session-observable.service';

@Injectable({
  providedIn: 'root'
})
//The @Injectable() decorator specifies that Angular can use this class in the DI(Dependency injection) system. 
//The metadata, providedIn: 'root', means that the HeroService is visible throughout the application.
export class CommonService {
  constructor(
    private sessionUtil: SessionUtil,
    private sessionObservableService: SessionObservableService,
    private authService: AuthService
  ) { }

  capitalizeFirstLetter(str: string): string {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }

  logout = (): void => {
    const session = this.sessionUtil.clearSession();
    this.sessionObservableService.emitSessionEvent(false);
    this.authService.deleteSession(session).subscribe(
      (res: any) => {
        //console.log("Session deleted successfully.");
      }
    )
  }
}
