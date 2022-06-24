import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OnboardingRoutingModule } from './onboarding-routing.module';
import { OnboardingComponent } from './onboarding.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { AddressComponent } from './address/address.component';
import { MobileVerificationComponent } from './mobile-verification/mobile-verification.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialImportModule } from '../../utils/material.module'
import { MatNativeDateModule } from '@angular/material/core';
@NgModule({
  declarations: [
    OnboardingComponent,
    UserDetailsComponent,
    AddressComponent,
    MobileVerificationComponent
  ],
  imports: [
    CommonModule,
    OnboardingRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialImportModule,
    MatNativeDateModule
  ]
})
export class OnboardingModule { }
