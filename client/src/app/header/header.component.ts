import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faBell, faCartShopping, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/services/api-related/auth/auth.service';
import { AuthObservableService } from 'src/services/observables-related/auth-observable.service';
import { SessionObservableService } from 'src/services/observables-related/session-observable.service';
import SessionUtil from 'src/utils/session.util';
import ToastrUtil from 'src/utils/toastr.util';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  faBell = faBell;
  faCartShopping = faCartShopping;
  faMagnifyingGlass = faMagnifyingGlass;
  isAuthRoute: boolean = false;
  isSession: boolean = false;

  constructor(
    private router: Router,
    private authObservableService: AuthObservableService,
    private sessionUnit: SessionUtil,
    private sessionObservableService: SessionObservableService,
    private toastr: ToastrUtil,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.sessionObservableService.sessionEventListener().subscribe(info => {
      this.isSession = info;
    })
    this.authObservableService.authRouteEventListner().subscribe(info => {
      this.isAuthRoute = info;
    })
  }

  moveToLoginPage = (): void => {
    this.router.navigate(["/auth/login"]);
  }

  logout = (): void => {
    const session = this.sessionUnit.clearSession();
    this.toastr.showSuccess("Logged out successfully.");
    this.sessionObservableService.emitSessionEvent(false);
    this.authService.deleteSession(session).subscribe(
      (res: any) => {
        console.log("Session deleted successfully.");
      }
    )
  }
}
