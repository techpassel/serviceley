import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from 'src/services/common/common.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  isAuthRoute: boolean = false;
  constructor(private router: Router, private commonService: CommonService, private ref: ChangeDetectorRef) {
  }

  @ViewChild('homeSection', { static: false })
  homeSectionElement !: ElementRef;

  ngOnInit(): void {
    this.commonService.authRouteEventListner().subscribe(info => {
      this.isAuthRoute = info;
    })
  }

  ngAfterContentChecked() {
    this.ref.detectChanges();
  }

  sidebarClosed = () => {
    this.homeSectionElement.nativeElement.classList.toggle('dilate');
  }
}
