import { Component, OnInit } from '@angular/core';
import { AuthObservableService } from 'src/services/observables-related/auth-observable.service';

@Component({
  selector: 'app-onboarding',
  templateUrl: './onboarding.component.html',
  styleUrls: ['./onboarding.component.scss']
})
export class OnboardingComponent implements OnInit {

  constructor(private authObservableService: AuthObservableService) { }

  ngOnInit(): void {
    this.authObservableService.emitAuthRouteEvent(true);
  }
}
