import { Component, OnInit } from '@angular/core';
import { AuthObservableService } from 'src/services/observables-related/auth-observable.service';

@Component({
  selector: 'app-public',
  templateUrl: './public.component.html',
  styleUrls: ['./public.component.scss']
})
export class PublicComponent implements OnInit {

  constructor(private authObservableService: AuthObservableService) { }

  ngOnInit(): void {
    this.authObservableService.emitAuthRouteEvent(false);
  }

}
