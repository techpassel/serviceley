import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginRequestData } from 'src/models/login-request-data';
import { AuthService } from 'src/services/auth/auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss', '../auth.component.scss']
})
export class LoginComponent implements OnInit {
  signinForm!: FormGroup;
  submitted: boolean = false;
  isProcessing: boolean = false;
  loginResponseType: string | null = null;

  constructor(private formBuilder: FormBuilder, private authService: AuthService) { }

  ngOnInit(): void {
    this.initializeLoginForm();
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
    console.log("Calledddd");
    
    this.submitted = true;

    // return from here if form is invalid
    if (this.signinForm.invalid) {
      return;
    }

    this.isProcessing = true;
    let loginRequestData = new LoginRequestData();
    loginRequestData.email = this.signinForm.value.username;
    loginRequestData.password = this.signinForm.value.password;
    this.signin$(loginRequestData);
  }

  signin$(loginRequestData: LoginRequestData): void {
    this.authService.login(loginRequestData).subscribe(
      (response: any) => {
        console.log(response, "response");
        //We have to write logic here
        this.isProcessing = false;
      },
      (error: any) => {
        console.log(error, "error");

        this.isProcessing = false;
      }
    );
  }

  onKeyUp(type: any) {
    if ((type === 'username' && this.loginResponseType === 'invalidUser') ||
      (type === 'pass' && this.loginResponseType === 'incorrectPassword')) {
      this.loginResponseType = null;
    }
  }

}
