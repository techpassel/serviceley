import { Component, OnInit } from '@angular/core';
import { AuthObservableService } from 'src/services/observables-related/auth-observable.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  constructor(private authObservableService: AuthObservableService) { }

  ngOnInit(): void {
    this.authObservableService.emitAuthRouteEvent(false);
  }

}
