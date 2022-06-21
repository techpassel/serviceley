import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OnboardingRoutingModule } from './onboarding-routing.module';
import { OnboardingComponent } from './onboarding.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { AddressComponent } from './address/address.component';
import { MobileVerificationComponent } from './mobile-verification/mobile-verification.component';


@NgModule({
  declarations: [
    OnboardingComponent,
    UserDetailsComponent,
    AddressComponent,
    MobileVerificationComponent
  ],
  imports: [
    CommonModule,
    OnboardingRoutingModule
  ]
})
export class OnboardingModule { }
