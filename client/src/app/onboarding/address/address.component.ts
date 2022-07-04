import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Address } from 'src/models/address-data';
import { OnboardingService } from 'src/services/api-related/onboarding/onboarding.service';
import SessionUtil from 'src/utils/session.util';
import { states } from 'src/utils/statelist.util';
import ToastrUtil from 'src/utils/toastr.util';
@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss', '../onboarding.component.scss']
})
export class AddressComponent implements OnInit {
  addressForm!: FormGroup;
  submitted = false;
  states = states;
  address!: Address;
  cities: any = [];
  userId!: number;
  isProcessing = false;
  constructor(
    private formBuilder: FormBuilder,
    private sessionUtil: SessionUtil,
    private onboardingService: OnboardingService,
    private toastr: ToastrUtil,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initializeAddressForm();
    const sessionData = this.sessionUtil.getSession();
    this.userId = sessionData?.id;
    this.getUserAddresses$();
  }

  initializeAddressForm = () => {
    this.addressForm = this.formBuilder.group({
      address: ['', Validators.required],
      landmark: ['', Validators.required],
      pincode: ['', Validators.required],
      state: ['', Validators.required],
      city: ['', Validators.required],
      area: ['', Validators.required]
    })
  }

  getUserAddresses$ = () => {
    this.onboardingService.getUserAddresses(this.userId).subscribe(
      (res: any) => {
        if (res.length > 0) {
          this.address = res.find((e: any) => e.isDefaultAddress == true);
          if (this.address.state && this.address.state != "") {
            let selectedState = this.states.find(e => e.id == this.address.state);
            this.cities = selectedState?.cities;
          }
          this.addressForm.patchValue(this.address);
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  /**
   * Getter for easy access to form fields
   */
  get f() { return this.addressForm.controls };

  onSubmit = () => {
    this.submitted = true;

    // return from here if form is invalid.
    if (this.addressForm.invalid) {
      console.log("Invalid data");
      return;
    }

    this.isProcessing = true;

    let addressData = new Address();
    addressData['id'] = this.address?.id;
    addressData['userId'] = this.userId;
    addressData['pincode'] = this.addressForm.value.pincode;
    addressData['country'] = "IN";
    addressData['state'] = this.addressForm.value.state;
    addressData['city'] = this.addressForm.value.city;
    addressData['area'] = this.addressForm.value.area;
    addressData['landmark'] = this.addressForm.value.landmark;
    addressData['address'] = this.addressForm.value.address;
    addressData['isDefaultAddress'] = true;
    this.updateAddress$(addressData);
  }

  updateAddress$ = (address: Address): any => {
    this.onboardingService.updateAddress(address).subscribe(
      (response: any) => {
        const session = this.sessionUtil.getSession();
        if (session.onboardingStage < 2) {
          session.onboardingStage = 2
        }
        this.sessionUtil.saveSession(session);
        this.toastr.showSuccess("Address updated successfully");
        this.router.navigateByUrl("/onboarding/mobile-verification");
        this.isProcessing = false;
      },
      (error: any) => {
        console.log(error);
        this.isProcessing = false;
      }
    )
  }

  setState = (event: any) => {
    let selectedState = this.states.find(e => e.id == event.target.value);
    //We can get the state value from following also. In that method we will not have to pass $event in method call. 
    //this.addressForm.value.city
    this.addressForm.controls.city.setValue("");
    this.cities = selectedState?.cities;
  }
}
