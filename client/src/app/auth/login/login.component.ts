import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { LoginRequestData } from 'src/models/login-request-data';
import { AuthService } from 'src/services/api-related/auth/auth.service';
import ToastrUtil from 'src/utils/toastr.util';
import SessionUtil from 'src/utils/session.util';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { SessionObservableService } from 'src/services/observables-related/session-observable.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss', '../auth.component.scss']
})
export class LoginComponent implements OnInit {
  signinForm!: FormGroup;
  loginRequestData = new LoginRequestData();
  submitted: boolean = false;
  isProcessing: boolean = false;
  loginResponseType: string | null = null;
  customError: string | null = null;
  invalidPhone: boolean = false;
  invalidEmail: boolean = false;
  invalidPassword: boolean = false;
  emailVerificationNeeded: boolean = false;
  emailVerificationResendSuccess: boolean = false;
  appName = environment.appName;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrUtil,
    private sessionUtil: SessionUtil,
    private router: Router,
    private sessionObservableService: SessionObservableService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    if (this.sessionUtil.sessionExist()) {
      this.redirect();
    }
    this.initializeLoginForm();
  }

  redirect = () => {
    this.activatedRoute.queryParams.subscribe(param => {
      if ('redirect' in param && !param['redirect'].includes("auth")) {
        this.router.navigateByUrl(param.redirect);
        //Pass querystring as - http://localhost:4200/auth/login?redirect=auth
      } else {
        const sessionData = this.sessionUtil.getSession();
        const onboardingStages = ['/onboarding', '/onboarding/address', '/onboarding/mobile-verification']
        if (sessionData.userType == "user") {
          if (sessionData.onboardingStage >= 0 && sessionData.onboardingStage <= 2)
            this.router.navigateByUrl(onboardingStages[sessionData.onboardingStage])
          else
            this.router.navigateByUrl("/user");
        }
      }
    })
  }

  /**
   * To initialize form validation rules.
   */
  initializeLoginForm = () => {
    this.signinForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  /**
   * Function to check if the given String is number or not.
   */
  isANumber(str: string): boolean {
    return !/\D/.test(str);
  }

  /**
   * Dynamically adding validation rule for phone and email based on what user have entered.
   */
  setEmailPhoneValidator(): void {
    let usernameField: any = this.signinForm.get('username');
    if (this.isANumber(usernameField.value)) {
      usernameField.setValidators([Validators.required, Validators.compose([Validators.minLength(10), Validators.maxLength(10)])]);
    } else {
      usernameField.setValidators([Validators.required, Validators.email]);
    }
    usernameField.updateValueAndValidity();
  }

  /**
   * Getter for easy access to form fields
   */
  get f() { return this.signinForm.controls; }

  onSubmit() {
    this.submitted = true;

    // return from here if form is invalid
    if (this.signinForm.invalid) {
      return;
    }

    this.loginRequestData.username = this.signinForm.value.username;
    this.loginRequestData.password = this.signinForm.value.password;
    this.signin$();
  }

  signin$(): void {
    this.isProcessing = true;
    this.authService.login(this.loginRequestData).subscribe(
      (response: any) => {
        this.sessionUtil.saveSession(response);
        this.isProcessing = false;
        this.toastr.showSuccess("Logged in successfully.");
        this.sessionObservableService.emitSessionEvent(true);
        this.redirect();
      },
      (error: any) => {
        if (error.status == 406) {
          switch (error.error) {
            case "InvalidPhoneException":
              this.invalidPhone = true;
              this.toastr.showError("Provided mobile number is not registered with us. Please check your number")
              break;
            case "InvalidEmailException":
              this.invalidEmail = true;
              this.toastr.showError("Provided email is not registered with us. Please check your email.")
              break;
            case "InvalidPasswordException":
              this.invalidPassword = true;
              this.toastr.showError("Password is incorrect. Please check your email and password.")
              break;
            case "EmailNotVerifiedException":
              this.emailVerificationNeeded = true;
              break;
            default:
              break;
          }
        } else if (error.status == 401 && error.error == "InactiveUserException") {
          this.customError = "Your account is deactivated now. Please contact support team.";
          this.toastr.showError("Your account is deactivated now. Please contact support team.")
        } else {
          this.toastr.showError(error.error);
        }
        this.isProcessing = false;
      }
    );
  }

  onKeyUp(type: any) {
    if (type === 'username') {
      this.invalidEmail = false;
      this.invalidPhone = false;
    }
    if (type === 'pass') {
      this.invalidPassword = false;
    }
  }

  resendVerificationLink() {
    this.authService.resendActivationEmail(this.loginRequestData.username).subscribe(
      (response: any) => {
        this.emailVerificationResendSuccess = true;
        this.toastr.showSuccess(response);
      },
      (error: any) => {
        this.toastr.showError(error.error);
      }
    )

  }

  resetLogin() {
    this.initializeLoginForm();
    this.loginRequestData = new LoginRequestData();
    this.submitted = false;
    this.isProcessing = false;
    this.loginResponseType = null;
    this.customError = null;
    this.invalidPhone = false;
    this.invalidEmail = false;
    this.invalidPassword = false;
    this.emailVerificationNeeded = false;
    this.emailVerificationResendSuccess = false;
  }

}
