import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SignupRequestData } from 'src/models/signup-request-data';
import { AuthService } from 'src/services/auth/auth.service';
import { CommonService } from 'src/services/common/common.service';
import { ToastrUtil } from 'src/utils/toastr.util';

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
  isUserRegistered: boolean = false;
  customError: string | null = null;
  user: SignupRequestData = new SignupRequestData();

  constructor(
    private formBuilder: FormBuilder,
    private commonService: CommonService,
    private authService: AuthService,
    private toastr: ToastrUtil
  ) { }

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
    this.user.firstName = this.commonService.capitalizeFirstLetter(this.signupForm.value.firstName);
    this.user.lastName = this.commonService.capitalizeFirstLetter(this.signupForm.value.lastName);
    this.user.email = this.signupForm.value.email;
    this.user.phone = this.signupForm.value.phone;
    this.user.password = this.signupForm.value.password;
    this.signup$();
  }

  signup$(): void {
    this.authService.signup(this.user).subscribe((response: any) => {
      this.toastr.showSuccess("Congratulations! You have registered successfully.");
      this.isUserRegistered = true;
      this.isProcessing = false;
    },
      (error: any) => {
        this.isProcessing = false;
        if (error.status == 400) {
          switch (error.error) {
            case "Email already exist":
              this.emailExists = true;
              break;
            case "Phone already exist":
              this.phoneExists = true;
              break;
            default:
              this.customError = error.error;
              this.toastr.showError(error.error);
              break;
          }
        }
      });
  }

}
