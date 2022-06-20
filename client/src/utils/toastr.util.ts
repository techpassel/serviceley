import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
    providedIn: 'root'
})
export default class ToastrUtil {
    constructor(private toastr: ToastrService) { }

    showSuccess(message: string | undefined, title?: string | undefined): void {
        this.toastr.success(message, title);
    }

    showError(message: string | undefined, title?: string | undefined): void {
        this.toastr.error(message, title);
    }

    showWarning(message: string | undefined, title?: string | undefined): void {
        this.toastr.warning(message, title);
    }

    showInfo(message: string | undefined, title?: string | undefined): void {
        this.toastr.info(message, title);
    }
}