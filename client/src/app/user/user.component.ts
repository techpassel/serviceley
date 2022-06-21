import { Component, OnInit } from '@angular/core';
import { AuthObservableService } from 'src/services/observables-related/auth-observable.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  constructor(private authObservableService: AuthObservableService) { }

  ngOnInit(): void {
    this.authObservableService.emitAuthRouteEvent(false);
  }

}
