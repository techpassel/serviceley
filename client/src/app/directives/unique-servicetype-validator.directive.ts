import { Directive, Input } from '@angular/core';
import {
  NG_ASYNC_VALIDATORS,
  AsyncValidator,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms'
import { catchError, map, Observable, of } from 'rxjs';
import { ManageService } from 'src/services/api-related/admin/service/manage-service.service';

@Directive({
  selector: '[uniqueServiceTypeValidator]',
  providers: [
    {
      provide: NG_ASYNC_VALIDATORS,
      useExisting: UniqueServiceTypeValidatorDirective,
      multi: true,
    },
  ],
})

export class UniqueServiceTypeValidatorDirective implements AsyncValidator {
  @Input("uniqueServiceTypeValidator") initialValue: any;

  constructor(private manageService: ManageService) { }

  validate(control: AbstractControl):
    Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    if (this.initialValue != "" && this.initialValue == control.value) {
      //It will be used incase of update operation. So that if serviceTypeName is not validated if it's current value and initial value are same.
      return of(null);
    }

    return this.manageService.isServiceExist(control.value)
      .pipe(
        map((exist: any) => {
          return exist ? { serviceExist: true } : null
        }),
        catchError(err => {
          return of(null)
        })
      )
  }
}

/*
Some important points to note here-
# It must be included into the "declarations" section of module file in which you want to use it.
# Since it is a Directive that is specified by using the @Directive decorator.
# With in the providers array first element is provide: NG_ASYNC_VALIDATORS which registers 
  the directive with the NG_ASYNC_VALIDATORS provider. That’s how Angular knows that this 
  directive has to be used as an asynchronous validator.
# Second element useExisting: UniqueEmailValidatorDirective instructs that an existing 
  instance of the directive has to be used rather than creating a new instance. if you use 
  useClass: UniqueEmailValidatorDirective, then you’d be registering a new class instance.
# By using Third element multi: true you can register many initializers using the same token. 
  In this context you can register a custom form validator using the built-in 
  NG_ASYNC_VALIDATORS token.
# This directive is used as a custom async validator that means it has to be added as an 
  attribute in a form control in the template. That attribute is added using the selector 
  (uniqueemailvalidator ) specified here.
# Though you can write the logic for validation with in the validate method here but better 
  to create a separate Angular Service class where you can add all of the required custom 
  validators and then call them. Here it is done like that by creating a Service class and 
  the service class in injected in the Directive in the constructor.
# The validate() method pipes the response through the map operator and transforms it into 
  a validation result which is a map of validation errors having key, value pair if 
  validation fails otherwise null. In our case that key, value pair is {emailTaken: true}
# Any potential errors are handles using the catchError operator, in which case null is 
  returned meaning no validation errors. You could handle the error differently and return 
  the ValidationError object instead.
*/