import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OnboardingGuard } from 'src/route-guards/onboarding.guard';
import { AddressComponent } from './address/address.component';
import { MobileVerificationComponent } from './mobile-verification/mobile-verification.component';
import { OnboardingComponent } from './onboarding.component';
import { UserDetailsComponent } from './user-details/user-details.component';

const routes: Routes = [
  {
    path: '', component: OnboardingComponent, canActivate: [OnboardingGuard],
    children: [
      {
        path: '',
        component: UserDetailsComponent,
      },
      {
        path: 'address',
        component: AddressComponent,
      },
      {
        path: 'mobile-verification',
        component: MobileVerificationComponent,
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OnboardingRoutingModule { }
