import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SignupRequestData } from 'src/models/signup-request-data';
import { AuthService } from 'src/services/auth/auth.service';
import { CommonService } from 'src/services/common/common.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss', '../auth.component.scss']
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  submitted: boolean = false;
  isPasswordMismatched: boolean = false;
  isProcessing: boolean = false;
  emailExists: boolean = false;
  phoneExists: boolean = false;

  constructor(private formBuilder: FormBuilder, private commonService: CommonService, private authService: AuthService) { }

  ngOnInit(): void {
    this.initializeSignupForm();
  }

  /**
   * To initialize form validation rules.
   */
  initializeSignupForm(): void {
    this.signupForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.compose([Validators.minLength(10), Validators.maxLength(10)])]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  /**
   * Getter for easy access to form fields
   */
  get f() { return this.signupForm.controls; }

  onKeyUp = (val: string) => {
    if (val == "confirmPassword") {
      this.isPasswordMismatched = false;
    }
    if (val == "email") {
      this.emailExists = false;
    }
    if (val == "phone") {
      this.phoneExists = false;
    }
  }

  onSubmit(): void {
    console.log("Submit called");
    
    this.submitted = true;

    // return from here if form is invalid.
    if (this.signupForm.invalid) {
      console.log("Invalid");
      return;
    }

    // return from here if password and confirm password don't match.
    if (this.signupForm.value.password !== this.signupForm.value.confirmPassword) {
      console.log("pwd mismatch");
      this.isPasswordMismatched = true;
      return;
    }

    this.isProcessing = true;
    let user = new SignupRequestData();
    user.firstName = this.commonService.capitalizeFirstLetter(this.signupForm.value.firstName);
    user.lastName = this.commonService.capitalizeFirstLetter(this.signupForm.value.lastName);
    user.email = this.signupForm.value.email;
    user.phone = this.signupForm.value.phone;
    user.password = this.signupForm.value.password;
    this.signup$(user);
  }

  signup$(user: SignupRequestData): void {
    this.authService.signup(user).subscribe((response: any) => {
      console.log(response, 'response');
      this.isProcessing = false;
    },
      (error: any) => {
        this.isProcessing = false;
        console.log(error, 'error');
      });
  }

}
