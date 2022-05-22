import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faBell, faCartShopping, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { AuthObservableService } from 'src/services/observables-related/auth-observable.service';

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

  constructor(private router: Router, private authObservableService: AuthObservableService) { }

  ngOnInit(): void {
    this.authObservableService.authRouteEventListner().subscribe(info => {
      this.isAuthRoute = info;
    })
  }

  moveToLoginPage = (): void => {
    this.router.navigate(["/auth/login"]);
  }

}
