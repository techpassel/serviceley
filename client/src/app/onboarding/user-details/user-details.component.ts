import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserData } from 'src/models/user-data';
import { OnboardingService } from 'src/services/api-related/onboarding/onboarding.service';
import SessionUtil from 'src/utils/session.util';
import * as moment from 'moment';
import { Router } from '@angular/router';
import ToastrUtil from 'src/utils/toastr.util';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss', '../onboarding.component.scss']
})
export class UserDetailsComponent implements OnInit {
  userDetailsForm!: FormGroup;
  submitted: boolean = false;
  isProcessing: boolean = false;
  userData!: UserData;
  constructor(
    private formBuilder: FormBuilder,
    private onboardingService: OnboardingService,
    private sessionUtil: SessionUtil,
    private router: Router,
    private toastr: ToastrUtil
  ) { }

  ngOnInit(): void {
    this.initializeUserDetailsForm();
    this.getUserData$();
  }

  /**
   * To initialize form validation rules.
   */
  initializeUserDetailsForm = () => {
    this.userDetailsForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      gender: ['', Validators.required],
      dob: ['', Validators.required],
      email: [
        { value: '', disabled: true },
        [Validators.required, Validators.email],
      ],
      phone: ['', Validators.required]
    })
  }

  /**
   * Getter for easy access to form fields
   */
  get f() { return this.userDetailsForm.controls };

  /**
   * To get user data
   */
  getUserData$ = () => {
    const sessionData = this.sessionUtil.getSession();
    this.onboardingService.getUser(sessionData.id).subscribe((response: any) => {
      // Converting dob in correct format.
      let dob = response.dob;
      let dobSplit = dob.split("-");
      response.dob = new Date(Number(dobSplit[2]), Number(dobSplit[1])-1, Number(dobSplit[0]));
      // Storing response id a variable for future use.
      this.userData = response;
      // To set values of all fileds in reactive form
      this.userDetailsForm.patchValue(response);
    },
      (error: any) => {
        console.log(error);
      }
    )
  }

  onSubmit = () => {
    this.submitted = true;
    this.isProcessing = true;

    // return from here if form is invalid.
    if (this.userDetailsForm.invalid) {
      return;
    }

    let user = new UserData();
    user['id'] = this.userData.id;
    user['firstName'] = this.userDetailsForm.value.firstName;
    user['lastName'] = this.userDetailsForm.value.lastName;
    user['phone'] = this.userDetailsForm.value.phone;
    user['dob'] = moment(this.userDetailsForm.value.dob).format("DD-MM-yyyy");
    user['gender'] = this.userDetailsForm.value.gender;
    //We are not sending "email" as it is not changed here and even the API we are calling is not entended to update email.
    //So no benefit of sending "email" with data.
    this.updateUser$(user);
  }

  updateUser$ = (user: UserData) => {
    this.onboardingService.updateUser(user).subscribe((response: any) => {
      const session = this.sessionUtil.getSession();
      if (session.onboardingStage < 1) {
        session.onboardingStage = 1
      }
      this.sessionUtil.saveSession(session);
      this.toastr.showSuccess("Userdata updated successfully");
      this.router.navigateByUrl("/onboarding/address");
      this.isProcessing = false;
    },
      (error: any) => {
        console.log(error);
        this.isProcessing = false;
      })
  }
}
