import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { OnboardingService } from 'src/services/api-related/onboarding/onboarding.service';
import SessionUtil from 'src/utils/session.util';
import ToastrUtil from 'src/utils/toastr.util';

@Component({
  selector: 'app-mobile-verification',
  templateUrl: './mobile-verification.component.html',
  styleUrls: ['./mobile-verification.component.scss', '../onboarding.component.scss']
})
export class MobileVerificationComponent implements OnInit {
  phone!: number | null;
  tempPhone!: number | null;
  isSendOtpBtnDisabled!: boolean;
  displayMessage = "";
  showOtpComponent = false;
  temp = false;
  config = {
    allowNumbersOnly: true,
    length: 6,
    isPasswordInput: false,
    disableAutoFocus: false,
    placeholder: '',
    inputStyles: {
      width: '35px',
      height: '35px',
      color: '#fc1456',
      'font-size': '20px',
      border: '2px solid #0300b3',
      outline: 'none'
    }
  };
  userId!: number;
  isModelOpen = false;
  stopTime = 0;
  otp = '';
  @ViewChild('f') form!: NgForm;
  @ViewChild("ngOtpInput", { static: false }) ngOtpInput: any;

  constructor(
    private sessionUtil: SessionUtil,
    private onboardingService: OnboardingService,
    private toastr: ToastrUtil,
    private router: Router
  ) { }

  ngOnInit(): void {
    const userInfo = this.sessionUtil.getSession();
    this.userId = userInfo?.id;
    this.getUserDetails$();
  }

  openUpdatePhoneModal = () => {
    this.showOtpComponent = false;
    this.tempPhone = this.phone;
    this.isModelOpen = true;
  }

  onOtpChange = (event: any) => {
    this.otp = event;
  }

  getUserDetails$ = () => {
    this.onboardingService.getUser(this.userId).subscribe(
      (response: any) => {
        this.phone = response.phone;
      },
      (error: any) => {
        console.log(error);
      }
    )
  }

  onSendBtnClick = () => {
    let data = { userId: this.userId, phone: this.phone }
    this.sendOtp$(data);
  }

  verifyOtp$ = () => {
    if (this.otp.length == 6) {
      const data = { userId: this.userId, otp: this.otp };
      this.onboardingService.verifyOtp(data).subscribe(
        (response: any) => {
          this.toastr.showSuccess("Mobile number verified successfully.");
          const session = this.sessionUtil.getSession();
          if (session.onboardingStage < 3) {
            session.onboardingStage = 3
          }
          this.sessionUtil.saveSession(session);
          this.router.navigateByUrl("/user");
        },
        (error: any) => {
          this.displayError(error);
        }
      )
    } else {
      this.toastr.showError("OTP must be of 6 digits.")
    }
  }

  closePopup = () => {
    this.isModelOpen = false;
    this.tempPhone = null;
  }

  updatePhone = () => {
    if (this.form.valid) {
      let data = { userId: this.userId, phone: this.tempPhone }
      this.sendOtp$(data, true);
    }
  }

  sendOtp$ = (data: any, isNumChanged = false) => {
    this.temp = false;
    this.onboardingService.updatePhone(data).subscribe(
      (response: any) => {
        if (isNumChanged) this.phone = this.tempPhone;
        this.showOtpComponent = true;
        this.isSendOtpBtnDisabled = true;
        this.isModelOpen = false;
        this.stopTime = 180;
        this.toastr.showSuccess("OTP has been sent successfully on your mobile number. Please verify your OTP.");
        let refreshtime = setInterval(() => {
          this.stopTime--;
          if (this.stopTime <= 0) {
            this.isSendOtpBtnDisabled = false;
            clearInterval(refreshtime);
          }
        }, 500);
        /*
          Following is the Temporary work around. As on production server we won't be able to send SMS. 
          As part of temporary workaround from server we are sending "OTP" back to the client. So we will populate 
          the OTP field directly with it.
        */
        setTimeout(() => {
          this.temp = true;
          this.tempPhone = null;
          this.ngOtpInput.setValue(response);
        }, 1000);
      },
      (error: any) => {
        this.displayError(error);
      }
    )
  }

  displayError = (error: any) => {
    const err = error?.error?.error ? error.error.error : error?.error ? error.error : error.message;
    this.toastr.showError(err);
  }
}
