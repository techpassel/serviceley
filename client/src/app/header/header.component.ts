import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faBell, faCartShopping, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { CommonService } from 'src/services/common/common.service';
import { AuthObservableService } from 'src/services/observables-related/auth-observable.service';
import { SessionObservableService } from 'src/services/observables-related/session-observable.service';
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
    private sessionObservableService: SessionObservableService,
    private commonService: CommonService,
    private toastr: ToastrUtil
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
    this.commonService.logout();
    this.toastr.showSuccess("Logged out successfully.");
    this.router.navigateByUrl("/");
  }
}
