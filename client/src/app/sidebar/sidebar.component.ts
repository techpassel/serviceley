import { Component, OnInit, ViewChild, ElementRef, EventEmitter, Output } from '@angular/core';
import { CommonService } from 'src/services/common/common.service';
import { SessionObservableService } from 'src/services/observables-related/session-observable.service';
import SessionUtil from 'src/utils/session.util';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  isSession!: boolean;
  name = "";
  userType = "";
  
  constructor(
    private sessionObservableService: SessionObservableService,
    private sessionUtil: SessionUtil,
    private commonService: CommonService
  ) { }

  ngOnInit(): void {
    this.sessionObservableService.sessionEventListener().subscribe(info => {
      this.isSession = info;
    });
    const userData = this.sessionUtil.getSession();
    this.name = userData.firstName + " " + userData.lastName;
    this.userType = this.commonService.capitalizeFirstLetter(userData.userType);
  }

  @Output() sidebarClosed: EventEmitter<string> = new EventEmitter();

  @ViewChild('first', { static: false })
  firstDropdown!: ElementRef;
  @ViewChild('second', { static: false })
  secondDropdown!: ElementRef;
  @ViewChild('third', { static: false })
  thirdDropdown!: ElementRef;
  @ViewChild('firstArrow', { static: false })
  firstArrow!: ElementRef;
  @ViewChild('secondArrow', { static: false })
  secondArrow!: ElementRef;
  @ViewChild('thirdArrow', { static: false })
  thirdArrow!: ElementRef;
  @ViewChild('sidebar', { static: false })
  sidebarElement!: ElementRef;

  toggleShowMenu = (val: string, currentElement: HTMLElement) => {
    let component = null;
    switch (val) {
      case 'first':
        component = this.firstDropdown
        break;
      case 'second':
        component = this.secondDropdown
        break;
      case 'third':
        component = this.thirdDropdown
        break;
      default:
        break;
    }
    if (component) {
      component.nativeElement.classList.toggle('show_menu');
      console.log(component?.nativeElement.classList);
    }
    currentElement.classList.toggle('transform_arrow');
  }

  toggleSidebar = (elem: HTMLElement) => {
    this.sidebarElement.nativeElement.classList.toggle('close');
    elem.classList.toggle('transform_arrow');
    this.sidebarClosed.emit();
    this.closeAllSubMenu();
  }

  closeAllSubMenu = () => {
    if (this.firstDropdown.nativeElement.classList.contains('show_menu')) {
      this.firstDropdown.nativeElement.classList.remove('show_menu');
      this.firstArrow.nativeElement.classList.toggle('transform_arrow')
    }
    if (this.secondDropdown.nativeElement.classList.contains('show_menu')) {
      this.secondDropdown.nativeElement.classList.remove('show_menu');
      this.secondArrow.nativeElement.classList.toggle('transform_arrow')
    }
    if (this.thirdDropdown.nativeElement.classList.contains('show_menu')) {
      this.thirdDropdown.nativeElement.classList.remove('show_menu');
      this.thirdArrow.nativeElement.classList.toggle('transform_arrow')
    }
  }
}
