import { Component, HostListener, OnInit } from '@angular/core';
import { AuthObservableService } from 'src/services/observables-related/auth-observable.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  constructor(private authObservableService: AuthObservableService) { }

  ngOnInit(): void {
    this.authObservableService.emitAuthRouteEvent(true);
  }

}
