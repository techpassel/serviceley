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
import { NgOtpInputModule } from 'ng-otp-input';
import { MatDialogModule } from '@angular/material/dialog';
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
    MatNativeDateModule,
    MatDialogModule,
    NgOtpInputModule
  ]
})
export class OnboardingModule { }
